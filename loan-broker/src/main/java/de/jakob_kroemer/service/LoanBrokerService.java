package de.jakob_kroemer.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.jakob_kroemer.domain.LoanRequest;
import de.jakob_kroemer.domain.BankResponse;
import de.jakob_kroemer.domain.ConfirmationMessage;
import de.jakob_kroemer.domain.CreditScoreResponse;
import de.jakob_kroemer.domain.EmailContent;
import de.jakob_kroemer.domain.LoanQuote;
import de.jakob_kroemer.config.AggregationResult;
import de.jakob_kroemer.controller.BankResponseHandler;

@Service
public class LoanBrokerService {

    @Autowired
    private CreditBureauService creditBureauService;

    @Autowired
    private BankCommunicationService bankCommunicationService;

    @Autowired
    private BankResponseHandler bankResponseHandler;

    private static final Logger logger = LoggerFactory.getLogger(LoanBrokerService.class);

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ConfirmationMessage processLoanRequest(LoanRequest request) {
        ConfirmationMessage confirmation = new ConfirmationMessage("Anfrage ist eingegangen. Sie werden per Mail spätestens in 10 Minuten benachrichtigt.");

        logger.info("2: LoanBrokerService Input{}", request.getCustomer().getSsn());

        String ssn = request.getCustomer().getSsn();
        String customerName = request.getCustomer().getFirstName() + " " + request.getCustomer().getLastName();
        float amount = request.getLoanAmount();
        int term = request.getTerm();
        UUID uuid = UUID.randomUUID();

        CreditScoreResponse creditScoreResponse = creditBureauService.getCreditScore(ssn, amount, term, uuid);
        int creditScore = creditScoreResponse.getScore();
        logger.info("6: LoanBrokerService score {}", creditScore);

        int expectedResponses = bankCommunicationService.sendLoanDetailsToBank(request, creditScore, uuid);

        // Send confirmation message immediately
        logger.info(confirmation.getMessage());

        // Asynchronous processing to send email after aggregation
        CompletableFuture.runAsync(() -> {
            LoanQuote ret = new LoanQuote();
            EmailContent emailContent = new EmailContent("Ihre Darlehensanfrage bei Krömer Bank mal Anders", "");
            try {
                if (expectedResponses > 0) {
                    bankResponseHandler.setLatch(uuid, expectedResponses);

                    try {
                        bankResponseHandler.awaitLatch(uuid, 15, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        logger.error("Error waiting for aggregation result", e);
                        ret.setStatus("Error waiting for aggregation result: " + e.getMessage());
                        Thread.currentThread().interrupt();
                    }

                    AggregationResult result = bankResponseHandler.getAggregationResult(uuid);
                    if (result != null) {
                        BankResponse bestQuote = result.getBestQuote();
                        if (bestQuote != null) {
                            ret.setRate(bestQuote.getInterestRate());
                            ret.setLender(bestQuote.getBankName());
                            ret.setQuoteDate(new Date());
                            ret.setExpirationDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30)));
                            logger.info("Best quote received from {} with rate {}", bestQuote.getBankName(), bestQuote.getInterestRate());
                            ret.setStatus("Best offer found");
                            emailContent.setBody(buildEmailBody(customerName, ret, request, uuid));
                        } else {
                            String message = "No quotes received for request with UUID: " + uuid;
                            logger.warn(message);
                            ret.setStatus(message);
                            emailContent.setBody(buildEmailBody(customerName, ret, request, uuid) + "<br><br>" + message);
                        }
                    } else {
                        String message = "No aggregation result found for UUID: " + uuid;
                        logger.warn(message);
                        ret.setStatus(message);
                        emailContent.setBody(buildEmailBody(customerName, ret, request, uuid) + "<br><br>" + message);
                    }
                } else {
                    String message = "No banks matched criteria, no requests sent.";
                    logger.warn(message);
                    ret.setStatus(message);
                    emailContent.setBody(buildEmailBody(customerName, ret, request, uuid) + "<br><br>" + message);
                }

                ret.setUuid(uuid);
                ret.setAmount(amount);
                ret.setTerm(term);

                // Send email with the aggregation result
                sendEmail(request.getCustomer().getEmail(), emailContent);

            } catch (Exception e) {
                logger.error("Exception during processing loan request", e);
                emailContent.setBody("An error occurred during processing: " + e.getMessage());
                sendEmail(request.getCustomer().getEmail(), emailContent);
            }
        });

        return confirmation;
    }

    private String buildEmailBody(String customerName, LoanQuote quote, LoanRequest request, UUID uuid) {
        String introText = "vielen Dank für Ihre Darlehensanfrage. Wir haben Ihre Anfrage erhalten und werden sie umgehend bearbeiten. " +
                "Nachfolgend finden Sie die Details Ihrer Anfrage sowie das beste verfügbare Angebot, falls vorhanden.";

        String noQuoteText = "Leider konnten wir kein passendes Angebot für Ihre Anfrage finden. Wir empfehlen Ihnen, es zu einem späteren Zeitpunkt erneut zu versuchen.";

        String quoteText = "Wir freuen uns, Ihnen das beste verfügbare Angebot für Ihre Anfrage vorstellen zu können.";

        return "<html>" +
                "<head>" +
                "<style>" +
                "body {font-family: Arial, sans-serif;}" +
                "table {width: 100%; border-collapse: collapse;}" +
                "td, th {padding: 10px; border: 1px solid #dddddd; text-align: left;}" +
                "th {background-color: #f2f2f2;}" +
                ".header {background-color: #4CAF50; color: white; text-align: center; padding: 10px;}" +
                ".footer {background-color: #f2f2f2; text-align: center; padding: 10px; font-size: 12px;}" +
                ".content {padding: 20px;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                "<h1>Krömer Bank mal Anders</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Sehr geehrte/r " + customerName + ",</p>" +
                "<p>" + introText + "</p>" +
                "<h2>Loan Request Details</h2>" +
                "<table>" +
                "<tr><th>Customer SSN</th><td>" + request.getCustomer().getSsn() + "</td></tr>" +
                "<tr><th>Loan Amount</th><td>" + request.getLoanAmount() + "</td></tr>" +
                "<tr><th>Loan Term</th><td>" + request.getTerm() + "</td></tr>" +
                "<tr><th>UUID</th><td>" + uuid.toString() + "</td></tr>" +
                "</table>" +
                "<h2>Loan Quote Details</h2>" +
                (quote.getLender() != null ? 
                "<p>" + quoteText + "</p>" +
                "<table>" +
                "<tr><th>Lender</th><td>" + quote.getLender() + "</td></tr>" +
                "<tr><th>Interest Rate</th><td>" + quote.getRate() + "</td></tr>" +
                "<tr><th>Quote Date</th><td>" + (quote.getQuoteDate() != null ? quote.getQuoteDate() : "N/A") + "</td></tr>" +
                "<tr><th>Expiration Date</th><td>" + (quote.getExpirationDate() != null ? quote.getExpirationDate() : "N/A") + "</td></tr>" +
                "<tr><th>Status</th><td>" + (quote.getStatus() != null ? quote.getStatus() : "N/A") + "</td></tr>" +
                "</table>" : 
                "<p>" + noQuoteText + "</p>") +
                "</div>" +
                "<div class='footer'>" +
                "© 2024 Krömer Bank mal Anders. All rights reserved." +
                "</div>" +
                "</body>" +
                "</html>";
    }

    private void sendEmail(String to, EmailContent content) {
        final String username = "BAM1@jakob-kroemer.de";
        final String password = "4mpXpAWs3XJsTft8";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.your-server.de");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            try {
				message.setFrom(new InternetAddress(username, "Krömer Bank mal Anders"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(content.getSubject());
            message.setContent(content.getBody(), "text/html; charset=utf-8");

            Transport.send(message);

            logger.info("Email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
