package de.jakob_kroemer.domain;

import java.io.Serializable;
import java.util.UUID;

public class BankResponse implements Serializable{
	private static final long serialVersionUID = -7029036442706103080L;
	private UUID uuid;
	private float interestRate;
	private String bankName;
	
	public BankResponse() {
    }
	
	public BankResponse(UUID uuid, float interestRrate, String bankName) {
		this.uuid = uuid;
		this.interestRate = interestRrate;
		this.bankName = bankName;
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

	public void setInterestRate(float interestRrate) {
		this.interestRate = interestRrate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
