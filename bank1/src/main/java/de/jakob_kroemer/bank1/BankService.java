package de.jakob_kroemer.bank1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BankService {
    
    private static final Logger logger = LoggerFactory.getLogger(BankService.class);
    
    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "loanRequestsChannel")
    public void receiveMessage(BankRequest bankRequest) {
        logger.info("Received bank request with SSN: {}", bankRequest.getSsn());
        logger.info("Received bank request UUID: {}", bankRequest.getUuid());
        
        // Erzeuge zufälligen Zinssatz
        float interestRate = new Random().nextFloat() * 10; // Zufälliger Zinssatz zwischen 0.0 und 10.0
        //BankResponse response = new BankResponse(bankRequest.getUuid(), interestRate);
        
        // Senden der Antwort
        //jmsTemplate.convertAndSend("responseQueue", response);
        
        logger.info("Sent response with UUID: {} and Interest Rate: {}", 1, 2);
    }
}
