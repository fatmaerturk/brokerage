package com.ing.brokerage.service;

import com.ing.brokerage.dto.OrderRequest;
import com.ing.brokerage.model.Customer;
import com.ing.brokerage.model.Order;
import com.ing.brokerage.repository.CustomerRepository;
import com.ing.brokerage.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(OrderRequest orderRequest) {
        Customer customer = customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(() ->
                new IllegalArgumentException("Customer not found with ID: " + orderRequest.getCustomerId()));

        Order order = new Order(
                customer,
                orderRequest.getAssetName(),
                orderRequest.getOrderSide(),
                orderRequest.getSize(),
                orderRequest.getPrice()
        );

        return orderRepository.save(order);
    }
}