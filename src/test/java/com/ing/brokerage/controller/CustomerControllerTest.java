package com.ing.brokerage.controller;

import com.ing.brokerage.dto.LoginRequest;
import com.ing.brokerage.entity.Customer;
import com.ing.brokerage.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerControllerTest {

    private CustomerController customerController;
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerService = mock(CustomerService.class);
        customerController = new CustomerController();
        customerController.customerService = customerService;
    }

    @Test
    public void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        when(customerService.authenticate(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(true);

        ResponseEntity<String> response = customerController.login(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody());
    }

    @Test
    public void testLoginFailure() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        when(customerService.authenticate(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(false);

        ResponseEntity<String> response = customerController.login(loginRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    public void testGetCustomerById() {
        String customerId = "customer1";
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerService.getCustomerById(customerId)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }
}