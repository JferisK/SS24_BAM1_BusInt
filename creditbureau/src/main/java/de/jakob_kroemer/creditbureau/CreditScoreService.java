package de.jakob_kroemer.creditbureau;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
public class CreditScoreService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CreditScoreResponse getCreditScore(Timestamp timestamp, String uuid, String ssn, float amount, int term, int score) {
        String sql = "INSERT INTO CreditOffers (timestamp, UUID, ssn, amount, term, score) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
        		sql, 
        		timestamp, 
        		uuid.toString(), 
        		ssn, 
        		amount, 
        		term, 
        		score
		);

        return new CreditScoreResponse(uuid, score);
    }
}
