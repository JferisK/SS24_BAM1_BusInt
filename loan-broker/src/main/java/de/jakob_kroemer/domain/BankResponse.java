package de.jakob_kroemer.domain;

import java.io.Serializable;

public class BankResponse implements Serializable{
	private static final long serialVersionUID = -7029036442706103080L;
	private String uuid;
	private float interestRrate;
	
	public BankResponse() {
    }
	
	public BankResponse(String uuid, float interestRrate) {
		this.uuid = uuid;
		this.interestRrate = interestRrate;
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
}
