package de.jakob_kroemer.domain;

import java.util.Date;
import java.util.UUID;

public class LoanQuote {
    private String lender;
    private Date quoteDate;
    private Date expirationDate;
    private double amount;
    private int term;
    private float rate;
    private UUID uuid;
    private String status;

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public Date getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(Date quoteDate) {
        this.quoteDate = quoteDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoanQuote{" +
                "lender='" + lender + '\'' +
                ", quoteDate=" + quoteDate +
                ", expirationDate=" + expirationDate +
                ", amount=" + amount +
                ", term=" + term +
                ", rate=" + rate +
                ", uuid=" + uuid +
                ", status='" + status + '\'' +
                '}';
    }
}
