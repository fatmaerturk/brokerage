package com.ing.brokerage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * This is the primary entry point for the Brokerage Firm backend application.
 * It is built using Spring Boot and offers API endpoints
 * for handling stock orders, assets, and customer management.
 *
 * Features:
 * - Order creation, listing, and cancellation.
 * - Customer authentication and authorization.
 * - Admin order matching functionality.
 * - H2 database for persistent storage.
 *
 * Running Instructions:
 * 1. Build the project using Maven:
 *    mvn clean install
 *
 * 2. Run the application:
 *    mvn spring-boot:run
 *
 * 3. Access H2 database console:
 *    URL: http://localhost:8080/h2-console
 *    JDBC URL: jdbc:h2:mem:brokage_db
 *    Username: sa
 *    Password: (leave blank)
 */
@SpringBootApplication
public class BrokerageApplication {

	public static void main(String[] args) {
		// Start the Spring Boot application
		SpringApplication.run(BrokerageApplication.class, args);
	}

}
