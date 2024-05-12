package de.jakob_kroemer.bank1;

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
    
    private Random random 					= new Random();
    private static final Logger logger 		= LoggerFactory.getLogger(BankService.class);
    private static final String BANK_NAME 	= System.getProperty("bank.name", "Default Bank");

    @JmsListener(destination = "loanRequestsChannel")
    public void receiveMessage(BankRequest bankRequest) {
        logger.info("Received bank request with SSN: {} & UUID: {}", bankRequest.getSsn(), bankRequest.getUuid());
        float interestRate = random.nextFloat() * 10;
        int delay = (int) (Math.sqrt(random.nextDouble()) * 15); // Verzögert die Antwortzeit zwischen 0 und 120 Sekunden
        try {
            Thread.sleep(delay * 1000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        BankResponse response = new BankResponse(bankRequest.getUuid(), interestRate, BANK_NAME);
        jmsTemplate.convertAndSend("responseQueue", response);
        logger.info("Sent response with UUID: {} and Interest Rate: {}, Bank: {}", response.getUuid(), response.getInterestRrate(), response.getBankName());
    }
}

