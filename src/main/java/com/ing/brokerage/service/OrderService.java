package com.ing.brokerage.service;

import com.ing.brokerage.model.Order;
import com.ing.brokerage.model.Asset;
import com.ing.brokerage.repository.OrderRepository;
import com.ing.brokerage.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing order operations such as creating, listing, and canceling orders.
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AssetRepository assetRepository;
    /**
     * Creates a new order if the customer has sufficient balance/assets.
     * @param order The order to be created.
     * @return The created order, or null if validation fails.
     */
    public Order createOrder(Order order) {
        order.setStatus("PENDING");  // Set default status

        System.out.println("Processing Order: " + order);

        Optional<Asset> tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY");
        System.out.println("TRY Balance: " + tryAsset);

        if ("BUY".equals(order.getOrderSide())) {
            if (tryAsset.isPresent()) {
                System.out.println("Customer TRY Balance: " + tryAsset.get().getUsableSize());
                double requiredAmount = order.getSize() * order.getPrice().doubleValue();
                System.out.println("Required TRY: " + requiredAmount);

                if (tryAsset.get().getUsableSize() >= requiredAmount) {
                    tryAsset.get().setUsableSize(tryAsset.get().getUsableSize() - (int) requiredAmount);
                    assetRepository.save(tryAsset.get());

                    order.setStatus("PENDING");
                    System.out.println("Saving Order: " + order);
                    return orderRepository.save(order);
                } else {
                    System.out.println("Not enough TRY balance.");
                }
            } else {
                System.out.println("TRY asset not found.");
            }
        }

        return null;
    }

    /**
     * Retrieves all orders for a given customer.
     * @param customerId The ID of the customer.
     * @return List of orders.
     */
    public List<Order> listOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    /**
     * Cancels an order if it is still pending.
     * @param orderId The ID of the order to be canceled.
     */
    public void cancelOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent() && "PENDING".equals(order.get().getStatus())) {
            order.get().setStatus("CANCELED");
            orderRepository.save(order.get());
        }
    }
}

