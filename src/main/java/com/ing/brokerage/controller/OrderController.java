package com.ing.brokerage.controller;

import com.ing.brokerage.dto.OrderRequest;
import com.ing.brokerage.entity.Order;
import com.ing.brokerage.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order createdOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        boolean isCancelled = orderService.cancelOrder(orderId);
        if (isCancelled) {
            return ResponseEntity.ok("Order cancelled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Only pending orders can be cancelled.");
        }
    }
}