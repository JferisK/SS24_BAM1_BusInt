package de.jakob_kroemer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.jakob_kroemer.domain.CreditScoreResponse;

import java.util.Locale;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CreditBureauService {

    @Autowired
    private RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CreditBureauService.class);

    public CreditScoreResponse getCreditScore(String ssn, float amount, int term, UUID uuid) {    	
    	logger.info("3: CreditBureauService Input SSN: {}, Amount: {}, Term: {}", ssn, amount, term);
        // Nutze Locale.US um sicherzustellen, dass Dezimalzahlen immer mit einem Punkt als Trennzeichen formatiert werden
    	String url = String.format(Locale.US, "http://localhost:8081/creditScore?ssn=%s&amount=%.2f&term=%d&uuid=%s", ssn, amount, term, uuid.toString());
        
        CreditScoreResponse response = restTemplate.getForObject(url, CreditScoreResponse.class);
        logger.info("4: CreditBureauService Received credit score {} for SSN {}", response.getScore(), ssn);
        return response;
    }
}