package de.jakob_kroemer.domain;

import java.util.List;

public class CreditReport {

    private String customerId;
    private List<CreditScoreResponse> creditScores;

    public CreditReport(String customerId, List<CreditScoreResponse> creditScores) {
        this.customerId 	= customerId;
        this.creditScores 	= creditScores;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<CreditScoreResponse> getCreditScores() {
        return creditScores;
    }

    public void setCreditScores(List<CreditScoreResponse> creditScores) {
        this.creditScores = creditScores;
    }

    @Override
    public String toString() {
        return "CreditReport{" +
                "customerId='" + customerId + '\'' +
                ", creditScores=" + creditScores +
                '}';
    }
}
