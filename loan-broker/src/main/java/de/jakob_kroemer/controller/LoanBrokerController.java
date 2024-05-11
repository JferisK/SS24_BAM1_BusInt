package de.jakob_kroemer.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import de.jakob_kroemer.domain.LoanRequest;
import de.jakob_kroemer.domain.LoanQuote;
import de.jakob_kroemer.service.CreditBureauService;
import de.jakob_kroemer.service.LoanBrokerService; 

/**
 * Controller to handle loan requests and interactions via REST API.
 */
@RestController
public class LoanBrokerController {

    @Autowired
    private LoanBrokerService loanBrokerService;
    private static final Logger logger = LoggerFactory.getLogger(LoanBrokerController.class);

    /**
     * Endpoint to submit a loan request.
     * @param loanRequest The loan request details.
     * @return The loan quote with calculated interest rate and terms.
     */
    @PostMapping("/loan")
    public LoanQuote submitLoanRequest(@RequestBody LoanRequest loanRequest) {
    	logger.info("1: LoanBrokerController Request {}", loanRequest.toString()); // Using info level for demonstration
        return loanBrokerService.processLoanRequest(loanRequest);
    }
    
    @GetMapping("/greeting")
    public String greeting() {
        return "Hello, World!";
    }
}
