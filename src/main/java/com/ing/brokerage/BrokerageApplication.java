package com.ing.brokerage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * This is the primary entry point for the Brokerage Firm backend application.
 * It is built using Spring Boot and offers API endpoints
 * for handling stock orders, assets, and customer management.
 */
@SpringBootApplication
public class BrokerageApplication {

	public static void main(String[] args) {
		// Start the Spring Boot application
		SpringApplication.run(BrokerageApplication.class, args);
	}

}
