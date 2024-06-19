package de.jakob_kroemer.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import de.jakob_kroemer.domain.LoanRequest;
import de.jakob_kroemer.domain.ConfirmationMessage;
import de.jakob_kroemer.domain.LoanQuote;
import de.jakob_kroemer.service.LoanBrokerService;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Controller to handle loan requests and interactions via REST API.
 */
@RestController
public class LoanBrokerController {

    @Autowired
    private LoanBrokerService loanBrokerService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(LoanBrokerController.class);

    /**
     * Endpoint to submit a loan request.
     * @param loanRequest The loan request details.
     * @return The loan quote with calculated interest rate and terms.
     */
    @PostMapping("/loan")
    public ConfirmationMessage submitLoanRequest(@RequestBody LoanRequest loanRequest) {
        logger.info("1: LoanBrokerController Request {}", loanRequest.toString()); // Using info level for demonstration
        return loanBrokerService.processLoanRequest(loanRequest);
    }

    @GetMapping("/loan/{id}")
    public LoanQuote getLoanRequest(@PathVariable UUID id) {
        String sql = "SELECT * FROM loan_requests WHERE id = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, id.toString());

        if (rows.isEmpty()) {
            return null;
        }

        Map<String, Object> rs = rows.get(0);
        LoanQuote loanQuote = new LoanQuote();
        loanQuote.setLender((String) rs.get("lender"));
        loanQuote.setQuoteDate((java.util.Date) rs.get("quote_date"));
        loanQuote.setExpirationDate((java.util.Date) rs.get("expiration_date"));
        loanQuote.setAmount(rs.get("loan_amount") != null ? ((Number) rs.get("loan_amount")).doubleValue() : 0.0);
        loanQuote.setTerm(rs.get("loan_term") != null ? ((Number) rs.get("loan_term")).intValue() : 0);
        loanQuote.setRate(rs.get("interest_rate") != null ? ((Number) rs.get("interest_rate")).floatValue() : 0.0f);
        loanQuote.setUuid(UUID.fromString((String) rs.get("id")));
        loanQuote.setStatus((String) rs.get("status"));

        return loanQuote;
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello, World!";
    }
}
