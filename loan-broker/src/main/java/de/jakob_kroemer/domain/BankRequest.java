package de.jakob_kroemer.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankRequest implements Serializable{
	private static final long serialVersionUID = 7641759662339279397L;
	private String uuid;
	private int term;
	private float amount;
	private int score;
	private String ssn;
	
	public BankRequest() {
    }

    public BankRequest(String uuid, int term, float amount, int score, String ssn) {
        this.uuid = uuid;
        this.term = term;
        this.amount = amount;
        this.score = score;
        this.ssn = ssn;
    }
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
}
