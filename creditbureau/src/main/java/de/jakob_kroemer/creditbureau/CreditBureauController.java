package de.jakob_kroemer.creditbureau;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
public class CreditBureauController {

    private static final Logger logger = LoggerFactory.getLogger(CreditBureauController.class);
    @Autowired
    private CreditScoreService creditScoreService;

    @GetMapping("/creditScore")
    @Cacheable(value = "creditScores", key = "#ssn.concat('-').concat(#amount).concat('-').concat(#term)")
    public CreditScoreResponse getCreditScore(@RequestParam String ssn, @RequestParam float amount, @RequestParam int term, @RequestParam String uuid) {
        logger.info("Received request for credit score with SSN: {}, Amount: {}, Term: {}", ssn, amount, term);
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CreditScoreResponse response = creditScoreService.getCreditScore(timestamp, uuid, ssn, amount, term);
        logger.info("Sending credit score {} for SSN {}", response.getScore(), ssn);
        return response;
    }
}
