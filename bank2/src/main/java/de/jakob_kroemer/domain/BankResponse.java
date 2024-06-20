package de.jakob_kroemer.domain;

import java.io.Serializable;

public class BankResponse implements Serializable {
    private static final long serialVersionUID = -7029036442706103080L;

    private String uuid;
    private float interestRate;
    private String bankName;

    public BankResponse() {}

    public BankResponse(String uuid, float interestRate, String bankName) {
        this.uuid         = uuid;
        this.interestRate = interestRate;
        this.bankName     = bankName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "BankResponse{" +
                "uuid='" + uuid + '\'' +
                ", interestRate=" + interestRate +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
