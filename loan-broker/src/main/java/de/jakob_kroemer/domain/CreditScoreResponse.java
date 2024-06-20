package de.jakob_kroemer.domain;

import java.util.UUID;

public class CreditScoreResponse {

	private UUID uuid;
    private int score;

    public CreditScoreResponse() {}
    
    public CreditScoreResponse(UUID uuid, int score) {
        this.uuid 	= uuid;
        this.score 	= score;
    }

    public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "CreditScoreResponse{" +
                "UUID='" + uuid + '\'' +
                ", score=" + score +
                '}';
    }
}
