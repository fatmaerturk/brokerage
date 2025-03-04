package com.ing.brokerage.controller;

import com.ing.brokerage.dto.OrderRequest;
import com.ing.brokerage.entity.Order;
import com.ing.brokerage.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/match-orders")
    public ResponseEntity<String> matchPendingOrders() {
        try {
            orderService.matchPendingOrders();
            return ResponseEntity.ok("Pending orders matched successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while matching orders.");
        }
    }
}