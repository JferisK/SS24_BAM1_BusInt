package de.jakob_kroemer.config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.jakob_kroemer.domain.BankResponse;

public class AggregationResult {
    private final List<BankResponse> responses = new ArrayList<>();

    public void addResponse(BankResponse response) {
        responses.add(response);
    }

    public List<BankResponse> getResponses() {
        return responses;
    }

    public BankResponse getBestQuote() {
        return responses.stream().min(Comparator.comparingDouble(BankResponse::getInterestRate)).orElse(null);
    }
}