package com.ing.brokerage.service;

import com.ing.brokerage.model.Customer;
import com.ing.brokerage.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service class for managing customer authentication and user-related operations.
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    /**
     * Finds a customer by their username.
     * @param username The username of the customer.
     * @return The customer entity if found.
     */
    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }
    /**
     * Registers a new customer in the system.
     * @param customer The customer to be saved.
     * @return The saved customer entity.
     */
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
