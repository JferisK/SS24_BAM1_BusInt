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
import java.util.concurrent.TimeUnit;

@Service
public class BankService {

    @Autowired
    private JmsTemplate jmsTemplate;

    private Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(BankService.class);
    private static final String BANK_NAME = "HSBC Bank";

    private static final int MIN_CREDIT_SCORE = 150;
    private static final int MIN_LOAN_AMOUNT = 5000;
    private static final int MAX_LOAN_AMOUNT = 25000;
    private static final int MIN_TERM = 3;
    private static final int MAX_TERM = 72;

    private static final int MIN_DELAY_MINUTES = 0;
    private static final int MAX_DELAY_MINUTES = 3;

    @JmsListener(destination = "hsbcBankChannel")
    public void receiveMessage(BankRequest bankRequest) {
        logger.info("Received bank request with SSN: {} & UUID: {}", bankRequest.getSsn(), bankRequest.getUuid());

        if (bankRequest.getScore() >= MIN_CREDIT_SCORE &&
            bankRequest.getAmount() >= MIN_LOAN_AMOUNT && bankRequest.getAmount() <= MAX_LOAN_AMOUNT &&
            bankRequest.getTerm() >= MIN_TERM && bankRequest.getTerm() <= MAX_TERM) {

            float interestRate = random.nextFloat() * 10;
            int delayMinutes = MIN_DELAY_MINUTES + random.nextInt(MAX_DELAY_MINUTES - MIN_DELAY_MINUTES + 1);
            try {
                TimeUnit.MINUTES.sleep(delayMinutes);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            BankResponse response = new BankResponse(bankRequest.getUuid(), interestRate, BANK_NAME);
            jmsTemplate.convertAndSend("responseQueue", response);
            logger.info("Sent response with UUID: {} and Interest Rate: {}, Bank: {}", response.getUuid(), response.getInterestRate(), response.getBankName());
        } else {
            logger.info("Bank request does not meet the criteria: {}", bankRequest);
        }
    }
}


