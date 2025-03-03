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
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Order createOrder(OrderRequest orderRequest) {
        if (!"TRY".equals(orderRequest.getAssetName())) {
            throw new IllegalArgumentException("Orders can only be placed for the TRY asset.");
        }

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

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public boolean cancelOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (order.getStatus() == OrderStatus.PENDING) {
                orderRepository.delete(order);
                return true;
            }
        }
        return false;
    }

    public List<Order> listOrders(String customerId, Date startDate, Date endDate) {
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);
    }
}