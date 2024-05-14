package de.jakob_kroemer.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import de.jakob_kroemer.domain.LoanRequest;
import de.jakob_kroemer.domain.BankRequest;

@Service
public class BankCommunicationService {

    @Autowired
    private JmsTemplate jmsTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(BankCommunicationService.class);

    public void sendLoanDetailsToBank(LoanRequest request, int creditScore, UUID uuid) {
        BankRequest bankRequest = new BankRequest(
            uuid.toString(),
            request.getTerm(),
            request.getLoanAmount(),
            creditScore,
            request.getCustomer().getSsn()
        );

        jmsTemplate.convertAndSend("loanRequestsChannel", bankRequest);

        logger.info("7: Send to Banks {}", bankRequest); 
    }
}
