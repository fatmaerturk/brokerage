package com.ing.brokerage.service;

import com.ing.brokerage.model.Customer;
import com.ing.brokerage.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service for handling customer-related operations such as authentication and user management.
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
