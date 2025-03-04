package com.ing.brokerage.controller;

import com.ing.brokerage.dto.OrderRequest;
import com.ing.brokerage.entity.Order;
import com.ing.brokerage.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderControllerTest {

    @Autowired
    private OrderController orderController;

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = mock(OrderService.class);
        orderController = new OrderController();
        orderController.orderService = orderService;
    }

    @Test
    public void testCreateOrderSuccess() {
        OrderRequest orderRequest = new OrderRequest();
        Order order = new Order();

        when(orderService.createOrder(orderRequest)).thenReturn(order);

        ResponseEntity<String> response = orderController.createOrder(orderRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order created successfully.", response.getBody());
    }

    @Test
    public void testCreateOrderFailure() {
        OrderRequest orderRequest = new OrderRequest();

        when(orderService.createOrder(orderRequest)).thenThrow(new IllegalArgumentException("Invalid order data"));

        ResponseEntity<String> response = orderController.createOrder(orderRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid order data", response.getBody());
    }

    @Test
    public void testGetOrderByIdSuccess() {
        Long orderId = 1L;
        String customerId = "customer1";
        Order order = new Order();

        when(orderService.getOrderByIdAndCustomerId(orderId, customerId)).thenReturn(order);

        ResponseEntity<Order> response = orderController.getOrderById(orderId, customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testGetOrderByIdFailure() {
        Long orderId = 1L;
        String customerId = "customer1";

        when(orderService.getOrderByIdAndCustomerId(orderId, customerId)).thenReturn(null);

        ResponseEntity<Order> response = orderController.getOrderById(orderId, customerId);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testCancelOrderSuccess() {
        Long orderId = 1L;
        String customerId = "customer1";

        when(orderService.cancelOrder(orderId, customerId)).thenReturn(true);

        ResponseEntity<String> response = orderController.cancelOrder(orderId, customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order cancelled successfully.", response.getBody());
    }

    @Test
    public void testCancelOrderFailure() {
        Long orderId = 1L;
        String customerId = "customer1";

        when(orderService.cancelOrder(orderId, customerId)).thenReturn(false);

        ResponseEntity<String> response = orderController.cancelOrder(orderId, customerId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Only pending orders can be cancelled.", response.getBody());
    }

    @Test
    public void testCancelOrderException() {
        Long orderId = 1L;
        String customerId = "customer1";

        when(orderService.cancelOrder(orderId, customerId)).thenThrow(new IllegalArgumentException("Invalid order ID"));

        ResponseEntity<String> response = orderController.cancelOrder(orderId, customerId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid order ID", response.getBody());
    }

    @Test
    public void testListOrders() {
        String customerId = "customer1";
        Date startDate = new Date();
        Date endDate = new Date();
        List<Order> orders = List.of(new Order(), new Order());

        when(orderService.listOrders(customerId, startDate, endDate)).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.listOrders(customerId, startDate, endDate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }
}