package com.ing.brokerage.repository;

import com.ing.brokerage.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for managing Order entities in the database.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByCustomerIdAndStatus(Long customerId, String status);
}
