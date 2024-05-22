package de.jakob_kroemer.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import de.jakob_kroemer.domain.BankRequest;
import de.jakob_kroemer.domain.BankResponse;

import java.util.Random;

@Service
public class BankService {

    @Autowired
    private JmsTemplate jmsTemplate;

    private Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(BankService.class);
    private static final String BANK_NAME = "Commerzbank";

    private static final int MIN_CREDIT_SCORE = 750;
    private static final int MIN_LOAN_AMOUNT = 15000;
    private static final int MAX_LOAN_AMOUNT = 35000;
    private static final int MIN_TERM = 12;
    private static final int MAX_TERM = 48;

    @JmsListener(destination = "commerzbankChannel")
    public void receiveMessage(BankRequest bankRequest) {
        logger.info("Received bank request with SSN: {} & UUID: {}", bankRequest.getSsn(), bankRequest.getUuid());

        if (bankRequest.getScore() >= MIN_CREDIT_SCORE &&
            bankRequest.getAmount() >= MIN_LOAN_AMOUNT && bankRequest.getAmount() <= MAX_LOAN_AMOUNT &&
            bankRequest.getTerm() >= MIN_TERM && bankRequest.getTerm() <= MAX_TERM) {

            float interestRate = random.nextFloat() * 10;
            int delay = (int) (Math.sqrt(random.nextDouble()) * 15);
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            BankResponse response = new BankResponse(bankRequest.getUuid(), interestRate, BANK_NAME);
            jmsTemplate.convertAndSend("responseQueue", response);
            logger.info("Sent response with UUID: {} and Interest Rate: {}, Bank: {}", response.getUuid(), response.getInterestRrate(), response.getBankName());
        } else {
            logger.info("Bank request does not meet the criteria: {}", bankRequest);
        }
    }
}


