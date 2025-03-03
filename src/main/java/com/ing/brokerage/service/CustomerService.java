package com.ing.brokerage.service;

import com.ing.brokerage.entity.Customer;
import com.ing.brokerage.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer login(String customerId, String password) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new IllegalArgumentException("Customer not found with ID: " + customerId));

        if (!customer.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        return customer;
    }
}