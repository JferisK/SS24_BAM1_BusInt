package de.jakob_kroemer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class LoanBrokerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanBrokerApplication.class, args);
    }
}
