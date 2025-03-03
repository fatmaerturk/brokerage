package com.ing.brokerage.controller;

import com.ing.brokerage.dto.CustomerLoginRequest;
import com.ing.brokerage.model.Customer;
import com.ing.brokerage.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestBody CustomerLoginRequest loginRequest) {
        Customer customer = customerService.login(loginRequest.getCustomerId(), loginRequest.getPassword());
        return ResponseEntity.ok(customer);
    }
}