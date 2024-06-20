package de.jakob_kroemer.domain;

public class LoanRequest {
    private Customer customer;
    private float loanAmount;
    private int term;  // in months
    private int creditScore;

    public LoanRequest() {}

    public LoanRequest(Customer customer, float loanAmount, int term, int creditScore) {
        this.customer    = customer;
        this.loanAmount  = loanAmount;
        this.term        = term;
        this.creditScore = creditScore;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public float getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(float loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    @Override
    public String toString() {
        return "LoanRequest{" +
                "customer=" + customer +
                ", loanAmount=" + loanAmount +
                ", term=" + term +
                ", creditScore=" + creditScore +
                '}';
    }
}
