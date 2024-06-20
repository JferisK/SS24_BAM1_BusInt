package de.jakob_kroemer.creditbureau;

public class CreditScoreResponse {
    private String uuid;
    private int score;

    public CreditScoreResponse(String uuid, int score) {
        this.uuid  = uuid;
        this.score = score;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
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
                "uuid='" + uuid + '\'' +
                ", score=" + score +
                '}';
    }
}
