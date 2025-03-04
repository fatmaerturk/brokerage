package com.ing.brokerage.controller;

import com.ing.brokerage.dto.LoginRequest;
import com.ing.brokerage.entity.Customer;
import com.ing.brokerage.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = customerService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }
}