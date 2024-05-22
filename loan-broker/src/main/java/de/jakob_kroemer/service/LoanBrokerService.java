package de.jakob_kroemer.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import de.jakob_kroemer.domain.LoanRequest;
import de.jakob_kroemer.domain.CreditScoreResponse;
import de.jakob_kroemer.domain.LoanQuote;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class LoanBrokerService {
	
	@Autowired
	private CreditBureauService creditBureauService;
	
	@Autowired
    private BankCommunicationService bankCommunicationService;
	
	private static final Logger logger = LoggerFactory.getLogger(LoanBrokerService.class);

    public LoanQuote processLoanRequest(LoanRequest request) {
    	LoanQuote ret = new LoanQuote();
    	
    	logger.info("2: LoanBrokerService Input{}", request.getCustomer().getSsn());
    	
    	String 	ssn 		= request.getCustomer().getSsn();
    	float 	amount 		= request.getLoanAmount();
    	int		term		= request.getTerm();
        UUID uuid 			= UUID.randomUUID();
    	
    	CreditScoreResponse creditScoreResponse = creditBureauService.getCreditScore(ssn, amount, term, uuid);
    	int creditScore = creditScoreResponse.getScore();
    	//int creditScore = 820;
    	logger.info("6: LoanBrokerService score {}", creditScore); 
 
    	bankCommunicationService.sendLoanDetailsToBank(request, creditScore, uuid);
    	
    	ret.setRate(creditScore);
    	ret.setUUID(uuid);
    	ret.setAmount(amount);
    	ret.setTerm(term);
        return ret; 
    }
}
