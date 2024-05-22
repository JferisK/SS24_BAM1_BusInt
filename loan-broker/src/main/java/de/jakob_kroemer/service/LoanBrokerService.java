package de.jakob_kroemer.service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import de.jakob_kroemer.domain.LoanRequest;
import de.jakob_kroemer.domain.BankResponse;
import de.jakob_kroemer.domain.CreditScoreResponse;
import de.jakob_kroemer.domain.LoanQuote;
import de.jakob_kroemer.config.AggregationResult;
import de.jakob_kroemer.controller.BankResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class LoanBrokerService {

    @Autowired
    private CreditBureauService creditBureauService;

    @Autowired
    private BankCommunicationService bankCommunicationService;

    @Autowired
    private BankResponseHandler bankResponseHandler;

    private static final Logger logger = LoggerFactory.getLogger(LoanBrokerService.class);

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public LoanQuote processLoanRequest(LoanRequest request) {
        LoanQuote ret = new LoanQuote();

        logger.info("2: LoanBrokerService Input{}", request.getCustomer().getSsn());

        String ssn = request.getCustomer().getSsn();
        float amount = request.getLoanAmount();
        int term = request.getTerm();
        UUID uuid = UUID.randomUUID();

        CreditScoreResponse creditScoreResponse = creditBureauService.getCreditScore(ssn, amount, term, uuid);
        int creditScore = creditScoreResponse.getScore();
        //int creditScore = 820;
        logger.info("6: LoanBrokerService score {}", creditScore);

        int expectedResponses = bankCommunicationService.sendLoanDetailsToBank(request, creditScore, uuid);

        if (expectedResponses > 0) {
            bankResponseHandler.setLatch(uuid, expectedResponses);

            try {
                bankResponseHandler.awaitLatch(uuid, 15, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("Error waiting for aggregation result", e);
                Thread.currentThread().interrupt();
            }

            AggregationResult result = bankResponseHandler.getAggregationResult(uuid);
            if (result != null) {
                BankResponse bestQuote = result.getBestQuote();
                if (bestQuote != null) {
                    ret.setRate(bestQuote.getInterestRate());
                    ret.setLender(bestQuote.getBankName());
                    ret.setQuoteDate(new Date());
                    ret.setExpirationDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30))); 
                    logger.info("Best quote received from {} with rate {}", bestQuote.getBankName(), bestQuote.getInterestRate());
                } else {
                    logger.warn("No quotes received for request with UUID: {}", uuid);
                }
            } else {
                logger.warn("No aggregation result found for UUID: {}", uuid);
            }
        } else {
            logger.warn("No banks matched criteria, no requests sent.");
        }

        ret.setUUID(uuid);
        ret.setAmount(amount);
        ret.setTerm(term);
        return ret;
    }
}
