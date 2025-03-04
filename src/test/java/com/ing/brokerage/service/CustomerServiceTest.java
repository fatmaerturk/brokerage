package com.ing.brokerage.service;

import com.ing.brokerage.entity.Customer;
import com.ing.brokerage.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService();
        customerService.customerRepository = customerRepository;
    }

    @Test
    public void testAuthenticateSuccess() {
        String username = "username";
        String password = "password";
        Customer customer = new Customer();
        customer.setName(username);
        customer.setPassword(password);

        when(customerRepository.findByNameAndPassword(username, password)).thenReturn(Optional.of(customer));

        boolean isAuthenticated = customerService.authenticate(username, password);
        assertTrue(isAuthenticated);
    }

    @Test
    public void testAuthenticateFailure() {
        String username = "username";
        String password = "wrongPassword";

        when(customerRepository.findByNameAndPassword(username, password)).thenReturn(Optional.empty());

        boolean isAuthenticated = customerService.authenticate(username, password);
        assertFalse(isAuthenticated);
    }

    @Test
    public void testGetCustomerById() {
        String customerId = "customer1";
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer retrievedCustomer = customerService.getCustomerById(customerId);
        assertEquals(customer, retrievedCustomer);
    }

    @Test
    public void testGetCustomerByIdNotFound() {
        String customerId = "customer1";

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Customer retrievedCustomer = customerService.getCustomerById(customerId);
        assertEquals(null, retrievedCustomer);
    }
}