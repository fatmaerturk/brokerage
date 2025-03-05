# Brokerage Firm Backend API

## Overview
This project is a Java backend API for a brokerage firm. It allows employees to create, list, and cancel stock orders for customers. The API also provides endpoints for listing assets, customer login, and order matching.

## Endpoints

- **Create Order**  
  POST `/orders/create`  
  Creates an order for a given customer. For BUY orders, the customer's TRY asset's usable balance is checked and reserved. For SELL orders, the specified asset's usable balance is checked and reserved.

- **List Orders**  
  GET `/orders/list?customerId=&startDate=&endDate=`  
  Lists orders created by a given customer within a date range.

- **Delete Order (Cancel Order)**  
  DELETE `/orders/cancel/{orderId}?customerId`  
  Cancels a pending order and refunds the reserved asset balance. Only orders with PENDING status can be cancelled.

- **List Assets**  
  GET `/assets/list?customerId=`  
  Returns all asset information for a given customer.

- **Customer Login** (Bonus)  
  POST `/customers/login`  
  Endpoint for customers to login. Customers can then access and manipulate only their own data.

- **Match Order** (Bonus for Admin)  
  POST `/admin/match-orders`  
  Admin-only endpoint that matches a pending order:
    - For BUY orders: credits the purchased stock asset.
    - For SELL orders: credits the TRY asset with the sale proceeds.

## Security
- All endpoints are secured via basic HTTP authentication.
- The `/customers/login` endpoint is public.
- The `/admin/**` endpoints require an admin user.
- An in-memory admin user is configured with username "admin" and password "admin".

## Technologies
- Java 21
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Security
- H2 Database

## H2 Database Configuration
The project uses H2 in-memory database for development and testing purposes. The database configuration is specified in the `application.properties` file.

JDBC URL: jdbc:h2:mem:testdb
User Name:	sa
Password:	password 

You can access the H2 database console at `http://localhost:8080/h2-console` with the details provided above.

## Build and Run
1. Clone the repository.
2. In the project directory, run:
   ```
   ./mvnw clean install
   ```
3. To start the application, run:
   ```
   ./mvnw spring-boot:run
   ```
4. The API will be available at `http://localhost:8080`.

## Testing
Unit tests are included; run them with:
```
./mvnw test
```

## Postman Collection
You can find the Postman collection for this API [here](postman_collection.json). You can use the postman collection by importing this json file into postman.After importing the Postman collection, make sure to:
Select "Basic Auth" as the authentication type.
Use the following credentials:
Username: admin  Password:admin
