package de.jakob_kroemer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ImportResource("classpath:META-INF/spring/integration/loan-broker-config.xml")
public class LoanBrokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanBrokerApplication.class, args);
    }
    
	/*
	 * @Bean public RestTemplate restTemplate() { return new RestTemplate(); }
	 */
}
