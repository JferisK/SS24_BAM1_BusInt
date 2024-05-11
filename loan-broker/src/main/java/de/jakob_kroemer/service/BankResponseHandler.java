package de.jakob_kroemer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import de.jakob_kroemer.domain.BankResponse;

@Component
public class BankResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(BankResponseHandler.class);

    @JmsListener(destination = "responseQueue")
    public void receiveResponse(BankResponse response) {
        logger.info("Received response with UUID: {} and Interest Rate: {}", response.getUuid(), response.getInterestRrate());
        // Hier könnten weitere Verarbeitungen implementiert werden, z.B. das Aktualisieren einer Datenbank oder das Triggering weiterer Geschäftsprozesse.
    }
}
