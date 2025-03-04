package com.ing.brokerage.service;

import com.ing.brokerage.dto.OrderRequest;
import com.ing.brokerage.entity.Asset;
import com.ing.brokerage.entity.Customer;
import com.ing.brokerage.entity.Order;
import com.ing.brokerage.repository.AssetRepository;
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
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AssetRepository assetRepository;

    public Order createOrder(OrderRequest orderRequest) {
        if (!"TRY".equals(orderRequest.getAssetName())) {
            throw new IllegalArgumentException("Orders can only be placed for the TRY asset.");
        }

        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Asset customerAsset = assetRepository.findByCustomerIdAndAssetName(orderRequest.getCustomerId(), "TRY")
                .orElseThrow(() -> new IllegalArgumentException("Customer does not have TRY asset."));

        if ("BUY".equalsIgnoreCase(orderRequest.getOrderSide())) {
            double totalCost = orderRequest.getSize() * orderRequest.getPrice();
            if (customerAsset.getUsableSize() < totalCost) {
                throw new IllegalArgumentException("Not enough usable size in TRY asset.");
            }
            customerAsset.setUsableSize(customerAsset.getUsableSize() - totalCost);
        } else if ("SELL".equalsIgnoreCase(orderRequest.getOrderSide())) {
            Asset assetToSell = assetRepository.findByCustomerIdAndAssetName(orderRequest.getCustomerId(), orderRequest.getAssetName())
                    .orElseThrow(() -> new IllegalArgumentException("Customer does not have the asset to sell."));
            if (assetToSell.getUsableSize() < orderRequest.getSize()) {
                throw new IllegalArgumentException("Not enough usable size in the asset to sell.");
            }
            assetToSell.setUsableSize(assetToSell.getUsableSize() - orderRequest.getSize());
            customerAsset.setUsableSize(customerAsset.getUsableSize() + orderRequest.getSize() * orderRequest.getPrice());
            assetRepository.save(assetToSell);
        } else {
            throw new IllegalArgumentException("Invalid order side.");
        }
        assetRepository.save(customerAsset);
        Order order = new Order();
        order.setAssetName(orderRequest.getAssetName());
        order.setOrderSide(orderRequest.getOrderSide());
        order.setSize(orderRequest.getSize());
        order.setPrice(orderRequest.getPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(new Date());
        order.setCustomer(customer);

        return orderRepository.save(order);
    }

    public Order getOrderByIdAndCustomerId(Long orderId, String customerId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (order.getCustomer().getId().equals(customerId) || order.getCustomer().isAdmin()) {
                return order;
            }
        }
        return null;
    }

    public boolean cancelOrder(Long orderId, String customerId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (order.getCustomer().getId().equals(customerId) || order.getCustomer().isAdmin()) {
                if (order.getStatus() == OrderStatus.PENDING) {
                    Asset customerAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomer().getId(), "TRY")
                            .orElseThrow(() -> new IllegalArgumentException("Customer does not have TRY asset."));
                    if ("BUY".equalsIgnoreCase(order.getOrderSide())) {
                        double totalCost = order.getSize() * order.getPrice();
                        customerAsset.setUsableSize(customerAsset.getUsableSize() + totalCost);
                    } else if ("SELL".equalsIgnoreCase(order.getOrderSide())) {
                        Asset assetToSell = assetRepository.findByCustomerIdAndAssetName(order.getCustomer().getId(), order.getAssetName())
                                .orElseThrow(() -> new IllegalArgumentException("Customer does not have the asset to sell."));
                        assetToSell.setUsableSize(assetToSell.getUsableSize() + order.getSize());
                        customerAsset.setUsableSize(customerAsset.getUsableSize() - order.getSize() * order.getPrice());
                        assetRepository.save(assetToSell);
                    }
                    assetRepository.save(customerAsset);
                    orderRepository.delete(order);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Order> listOrders(String customerId, Date startDate, Date endDate) {
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);
    }

    public void matchPendingOrders() {
        List<Order> pendingOrders = orderRepository.findByStatus(OrderStatus.PENDING);

        for (Order order : pendingOrders) {
            Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomer().getId(), "TRY")
                    .orElseThrow(() -> new IllegalArgumentException("Customer does not have TRY asset."));
            Asset boughtAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomer().getId(), order.getAssetName())
                    .orElseThrow(() -> new IllegalArgumentException("Customer does not have the asset to buy/sell."));

            if (order.getOrderSide().equals("BUY")) {
                tryAsset.setUsableSize(tryAsset.getUsableSize() - order.getPrice() * order.getSize());
                boughtAsset.setTotalSize(boughtAsset.getTotalSize() + order.getSize());
                boughtAsset.setUsableSize(boughtAsset.getUsableSize() + order.getSize());
            } else if (order.getOrderSide().equals("SELL")) {
                tryAsset.setUsableSize(tryAsset.getUsableSize() + order.getPrice() * order.getSize());
                boughtAsset.setUsableSize(boughtAsset.getUsableSize() - order.getSize());
            }
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
            assetRepository.save(tryAsset);
            assetRepository.save(boughtAsset);
        }
    }
}