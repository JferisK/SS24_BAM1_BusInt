package de.jakob_kroemer.domain;

public class BankConfiguration {
    private String bankName;
    private String bankChannel;
    private float minCreditScore;
    private float minLoanAmount;
    private float maxLoanAmount;
    private int minTerm;
    private int maxTerm;

    public BankConfiguration() {}

    public BankConfiguration(String bankName, String bankChannel, float minCreditScore, float minLoanAmount, float maxLoanAmount, int minTerm, int maxTerm) {
        this.bankName       = bankName;
        this.bankChannel    = bankChannel;
        this.minCreditScore = minCreditScore;
        this.minLoanAmount  = minLoanAmount;
        this.maxLoanAmount  = maxLoanAmount;
        this.minTerm        = minTerm;
        this.maxTerm        = maxTerm;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankChannel() {
        return bankChannel;
    }

    public void setBankChannel(String bankChannel) {
        this.bankChannel = bankChannel;
    }

    public float getMinCreditScore() {
        return minCreditScore;
    }

    public void setMinCreditScore(float minCreditScore) {
        this.minCreditScore = minCreditScore;
    }

    public float getMinLoanAmount() {
        return minLoanAmount;
    }

    public void setMinLoanAmount(float minLoanAmount) {
        this.minLoanAmount = minLoanAmount;
    }

    public float getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(float maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public int getMinTerm() {
        return minTerm;
    }

    public void setMinTerm(int minTerm) {
        this.minTerm = minTerm;
    }

    public int getMaxTerm() {
        return maxTerm;
    }

    public void setMaxTerm(int maxTerm) {
        this.maxTerm = maxTerm;
    }

    @Override
    public String toString() {
        return "BankConfiguration{" +
                "bankName='" + bankName + '\'' +
                ", bankChannel='" + bankChannel + '\'' +
                ", minCreditScore=" + minCreditScore +
                ", minLoanAmount=" + minLoanAmount +
                ", maxLoanAmount=" + maxLoanAmount +
                ", minTerm=" + minTerm +
                ", maxTerm=" + maxTerm +
                '}';
    }
}
