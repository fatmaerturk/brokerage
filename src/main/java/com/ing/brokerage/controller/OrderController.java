package com.ing.brokerage.controller;

import com.ing.brokerage.model.Order;
import com.ing.brokerage.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for handling order-related operations.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    /**
     * Endpoint to create a new order.
     * @param order The order details.
     * @return The created order or bad request if validation fails.
     */


    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        System.out.println("Received Order Request: " + order.getAssetName());
        Order createdOrder = orderService.createOrder(order);
        return createdOrder != null ? ResponseEntity.ok(createdOrder) : ResponseEntity.badRequest().build();
    }

    /**
     * Endpoint to retrieve orders of a specific customer.
     * @param customerId The ID of the customer.
     * @return List of orders.
     */
    @GetMapping("/list/{customerId}")
    public ResponseEntity<List<Order>> listOrders(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.listOrders(customerId));
    }
    /**
     * Endpoint to cancel an order if it's still pending.
     * @param orderId The ID of the order to cancel.
     */
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
