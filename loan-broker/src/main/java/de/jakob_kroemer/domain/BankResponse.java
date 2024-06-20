package de.jakob_kroemer.domain;

import java.io.Serializable;
import java.util.UUID;

public class BankResponse implements Serializable {
    private static final long serialVersionUID = -7029036442706103080L;

    private UUID uuid;
    private float interestRate;
    private String bankName;

    public BankResponse() {}

    public BankResponse(UUID uuid, float interestRate, String bankName) {
        this.uuid         = uuid;
        this.interestRate = interestRate;
        this.bankName     = bankName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
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
                "uuid=" + uuid +
                ", interestRate=" + interestRate +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
