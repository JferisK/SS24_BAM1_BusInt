package de.jakob_kroemer.domain;

import java.io.Serializable;

public class BankResponse implements Serializable{
	private static final long serialVersionUID = -7029036442706103080L;
	private String uuid;
	private float interestRrate;
	private String bankName;
	
	public BankResponse() {
    }
	
	public BankResponse(String uuid, float interestRrate, String bankName) {
		this.uuid = uuid;
		this.interestRrate = interestRrate;
		this.bankName = bankName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public float getInterestRrate() {
		return interestRrate;
	}

	public void setInterestRrate(float interestRrate) {
		this.interestRrate = interestRrate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
