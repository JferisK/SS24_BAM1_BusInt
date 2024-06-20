package de.jakob_kroemer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import de.jakob_kroemer.config.AggregationResult;
import de.jakob_kroemer.domain.BankResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class BankResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(BankResponseHandler.class);
    private final Map<UUID, AggregationResult> responseMap = new ConcurrentHashMap<>();
    private final Map<UUID, CountDownLatch> latchMap = new ConcurrentHashMap<>();

    @JmsListener(destination = "responseQueue")
    public void receiveResponse(BankResponse response) {
        logger.info("Received response from {} with UUID: {} and Interest Rate: {}", response.getBankName(), response.getUuid(), response.getInterestRate());

        UUID uuid = response.getUuid();
        AggregationResult result = responseMap.computeIfAbsent(uuid, k -> new AggregationResult());
        result.addResponse(response);

        CountDownLatch latch = latchMap.get(uuid);
        if (latch != null) {
            latch.countDown();
        }
    }

    public AggregationResult getAggregationResult(UUID uuid) {
        return responseMap.get(uuid);
    }

    public void setLatch(UUID uuid, int count) {
        latchMap.put(uuid, new CountDownLatch(count));
    }

    public boolean awaitLatch(UUID uuid, long timeout, TimeUnit unit) throws InterruptedException {
        CountDownLatch latch = latchMap.get(uuid);
        if (latch != null) {
            return latch.await(timeout, unit);
        }
        return false;
    }
}
