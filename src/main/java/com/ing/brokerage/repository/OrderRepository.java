package com.ing.brokerage.repository;

import com.ing.brokerage.entity.Order;
import com.ing.brokerage.util.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndCreateDateBetween(String customerId, Date startDate, Date endDate);
    List<Order> findByStatus(OrderStatus status);
}