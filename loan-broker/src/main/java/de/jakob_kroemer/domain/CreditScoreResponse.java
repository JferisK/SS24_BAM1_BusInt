package de.jakob_kroemer.domain;

public class CreditScoreResponse {

    private String agency;
    private int score;

    public CreditScoreResponse(String agency, int score) {
        this.agency 	= agency;
        this.score 		= score;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
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
                "agency='" + agency + '\'' +
                ", score=" + score +
                '}';
    }
}
