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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminControllerTest {
    @Autowired
    private AdminController adminController;
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = mock(OrderService.class);
        adminController = new AdminController();
        adminController.orderService = orderService;
    }

    @Test
    public void testCreateOrder() {
        OrderRequest orderRequest = new OrderRequest();
        Order order = new Order();

        when(orderService.createOrder(orderRequest)).thenReturn(order);

        ResponseEntity<Order> response = adminController.createOrder(orderRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testMatchPendingOrdersSuccess() {
        doNothing().when(orderService).matchPendingOrders();

        ResponseEntity<String> response = adminController.matchPendingOrders();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pending orders matched successfully.", response.getBody());
    }

    @Test
    public void testMatchPendingOrdersFailure() {
        doThrow(new RuntimeException()).when(orderService).matchPendingOrders();

        ResponseEntity<String> response = adminController.matchPendingOrders();

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while matching orders.", response.getBody());
    }
}