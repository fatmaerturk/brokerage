package com.ing.brokerage.controller;

import com.ing.brokerage.model.Customer;
import com.ing.brokerage.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * REST Controller for managing customer authentication and user-related operations.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    /**
     * Endpoint to find customer details by username.
     * @param username The username of the customer.
     * @return The customer details if found.
     */
    @GetMapping("/find/{username}")
    public ResponseEntity<Optional<Customer>> findCustomer(@PathVariable String username) {
        return ResponseEntity.ok(customerService.findByUsername(username));
    }
    /**
     * Endpoint to register a new customer.
     * @param customer The customer registration details.
     * @return The registered customer entity.
     */
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }
}
