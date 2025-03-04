package com.ing.brokerage.service;

import com.ing.brokerage.dto.OrderRequest;
import com.ing.brokerage.entity.Asset;
import com.ing.brokerage.entity.Customer;
import com.ing.brokerage.entity.Order;
import com.ing.brokerage.repository.AssetRepository;
import com.ing.brokerage.repository.CustomerRepository;
import com.ing.brokerage.repository.OrderRepository;
import com.ing.brokerage.util.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    private OrderService orderService;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private AssetRepository assetRepository;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        customerRepository = mock(CustomerRepository.class);
        assetRepository = mock(AssetRepository.class);
        orderService = new OrderService();
        orderService.orderRepository = orderRepository;
        orderService.customerRepository = customerRepository;
        orderService.assetRepository = assetRepository;
    }

    @Test
    public void testCreateOrderSuccess() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId("customer1");
        orderRequest.setAssetName("TRY");
        orderRequest.setOrderSide("BUY");
        orderRequest.setSize(10);
        orderRequest.setPrice(100);

        Customer customer = new Customer();
        Asset asset = new Asset();
        asset.setUsableSize(2000);

        when(customerRepository.findById(orderRequest.getCustomerId())).thenReturn(Optional.of(customer));
        when(assetRepository.findByCustomerIdAndAssetName(orderRequest.getCustomerId(), "TRY")).thenReturn(Optional.of(asset));

        Order order = new Order();
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(orderRequest);

        assertNotNull(createdOrder);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(assetRepository, times(1)).save(asset);
    }

    @Test
    public void testCreateOrderFailureInvalidAsset() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId("customer1");
        orderRequest.setAssetName("USD");
        orderRequest.setOrderSide("BUY");
        orderRequest.setSize(10);
        orderRequest.setPrice(100);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(orderRequest);
        });

        assertEquals("Orders can only be placed for the TRY asset.", exception.getMessage());
    }

    @Test
    public void testGetOrderByIdAndCustomerIdSuccess() {
        Long orderId = 1L;
        String customerId = "customer1";
        Customer customer = new Customer();
        customer.setId(customerId);
        Order order = new Order();
        order.setCustomer(customer);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order retrievedOrder = orderService.getOrderByIdAndCustomerId(orderId, customerId);

        assertNotNull(retrievedOrder);
        assertEquals(order, retrievedOrder);
    }

    @Test
    public void testGetOrderByIdAndCustomerIdFailure() {
        Long orderId = 1L;
        String customerId = "customer1";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Order retrievedOrder = orderService.getOrderByIdAndCustomerId(orderId, customerId);

        assertNull(retrievedOrder);
    }

    @Test
    public void testCancelOrderSuccess() {
        Long orderId = 1L;
        String customerId = "customer1";
        Customer customer = new Customer();
        customer.setId(customerId);
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);

        Asset asset = new Asset();
        asset.setUsableSize(2000);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")).thenReturn(Optional.of(asset));

        boolean isCancelled = orderService.cancelOrder(orderId, customerId);

        assertTrue(isCancelled);
        verify(orderRepository, times(1)).delete(order);
        verify(assetRepository, times(1)).save(asset);
    }

    @Test
    public void testCancelOrderFailure() {
        Long orderId = 1L;
        String customerId = "customer1";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        boolean isCancelled = orderService.cancelOrder(orderId, customerId);

        assertFalse(isCancelled);
    }

    @Test
    public void testListOrders() {
        String customerId = "customer1";
        Date startDate = new Date();
        Date endDate = new Date();
        List<Order> orders = List.of(new Order(), new Order());

        when(orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate)).thenReturn(orders);

        List<Order> retrievedOrders = orderService.listOrders(customerId, startDate, endDate);

        assertNotNull(retrievedOrders);
        assertEquals(orders, retrievedOrders);
    }

    @Test
    public void testMatchPendingOrders() {
        Order order = new Order();
        order.setOrderSide("BUY");
        order.setSize(10);
        order.setPrice(100);
        Customer customer = new Customer();
        customer.setId("customer1");
        order.setCustomer(customer);

        List<Order> pendingOrders = List.of(order);
        Asset tryAsset = new Asset();
        tryAsset.setUsableSize(2000);
        Asset boughtAsset = new Asset();

        when(orderRepository.findByStatus(OrderStatus.PENDING)).thenReturn(pendingOrders);
        when(assetRepository.findByCustomerIdAndAssetName(anyString(), eq("TRY"))).thenReturn(Optional.of(tryAsset));
        when(assetRepository.findByCustomerIdAndAssetName(anyString(), eq(order.getAssetName()))).thenReturn(Optional.of(boughtAsset));

        orderService.matchPendingOrders();

        assertEquals(OrderStatus.COMPLETED, order.getStatus());
        verify(orderRepository, times(1)).save(order);
        verify(assetRepository, times(2)).save(any(Asset.class));
    }

    @Test
    public void testMatchPendingOrdersCustomerDoesNotHaveTryAsset() {
        Order order = new Order();
        order.setOrderSide("BUY");
        order.setSize(10);
        order.setPrice(100);
        Customer customer = new Customer();
        customer.setId("customer1");
        order.setCustomer(customer);

        List<Order> pendingOrders = List.of(order);

        when(orderRepository.findByStatus(OrderStatus.PENDING)).thenReturn(pendingOrders);
        when(assetRepository.findByCustomerIdAndAssetName(anyString(), eq("TRY"))).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.matchPendingOrders();
        });

        assertEquals("Customer does not have TRY asset.", exception.getMessage());
    }
}