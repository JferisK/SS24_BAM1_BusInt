package de.jakob_kroemer;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@ImportResource("classpath:META-INF/spring/integration/loan-broker-config.xml")
public class LoanBrokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanBrokerApplication.class, args);
    }
}
