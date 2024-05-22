package de.jakob_kroemer.service;

import de.jakob_kroemer.domain.BankConfiguration;
import de.jakob_kroemer.domain.LoanRequest;
import de.jakob_kroemer.domain.BankRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class BankCommunicationService {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(BankCommunicationService.class);

    public void sendLoanDetailsToBank(LoanRequest request, int creditScore, UUID uuid) {
        List<BankConfiguration> configs = fetchBankConfigurations();
        BankRequest bankRequest = new BankRequest(
            uuid.toString(),
            request.getTerm(),
            request.getLoanAmount(),
            creditScore,
            request.getCustomer().getSsn()
        );

        boolean matched = false;
        for (BankConfiguration config : configs) {
            if (matchesCriteria(request, creditScore, config)) {
                jmsTemplate.convertAndSend(config.getBankChannel(), bankRequest);
                logger.info("Sent loan details to {} via channel {}", config.getBankName(), config.getBankChannel());
                matched = true;
            }
        }

        if (!matched) {
            logger.warn("No bank matched criteria for request {}", request);
        }
    }

    private List<BankConfiguration> fetchBankConfigurations() {
        String sql = "SELECT bankName, bankChannel, minCreditScore, minLoanAmount, maxLoanAmount, minTerm, maxTerm FROM BankChannel";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToBankConfiguration(rs));
    }

    private BankConfiguration mapRowToBankConfiguration(ResultSet rs) throws SQLException {
        BankConfiguration config = new BankConfiguration();
        config.setBankName(rs.getString("bankName"));
        config.setBankChannel(rs.getString("bankChannel"));
        config.setMinCreditScore(rs.getFloat("minCreditScore"));
        config.setMinLoanAmount(rs.getFloat("minLoanAmount"));
        config.setMaxLoanAmount(rs.getFloat("maxLoanAmount"));
        config.setMinTerm(rs.getInt("minTerm"));
        config.setMaxTerm(rs.getInt("maxTerm"));
        return config;
    }

    private boolean matchesCriteria(LoanRequest request, int creditScore, BankConfiguration config) {
        return creditScore >= config.getMinCreditScore() &&
               request.getLoanAmount() >= config.getMinLoanAmount() &&
               request.getLoanAmount() <= config.getMaxLoanAmount() &&
               request.getTerm() >= config.getMinTerm() &&
               request.getTerm() <= config.getMaxTerm();
    }
}
