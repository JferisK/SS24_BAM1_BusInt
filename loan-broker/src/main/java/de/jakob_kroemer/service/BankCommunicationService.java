package de.jakob_kroemer.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import de.jakob_kroemer.domain.LoanRequest;
import de.jakob_kroemer.domain.BankRequest;

@Service
public class BankCommunicationService {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendLoanDetailsToBank(LoanRequest request, int creditScore, UUID uuid) {
        BankRequest bankRequest = new BankRequest(
            uuid.toString(),
            request.getTerm(),
            request.getLoanAmount(),
            creditScore,
            request.getCustomer().getSsn()
        );

        jmsTemplate.convertAndSend("loanRequestsChannel", bankRequest, message -> {
        	message.setStringProperty("_type", "de.jakob_kroemer.bank1.BankRequest");
            return message;
        });

    }
}
