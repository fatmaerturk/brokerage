package com.ing.brokerage.service;

import com.ing.brokerage.dto.OrderRequest;
import com.ing.brokerage.entity.Customer;
import com.ing.brokerage.entity.Order;
import com.ing.brokerage.repository.CustomerRepository;
import com.ing.brokerage.repository.OrderRepository;
import com.ing.brokerage.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setAssetName(orderRequest.getAssetName());
        order.setOrderSide(orderRequest.getOrderSide());
        order.setSize(orderRequest.getSize());
        order.setPrice(orderRequest.getPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(new Date());

        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        order.setCustomer(customer);

        return orderRepository.save(order);
    }
}