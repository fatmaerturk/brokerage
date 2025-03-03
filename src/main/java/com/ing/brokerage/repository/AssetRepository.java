package com.ing.brokerage.repository;

import com.ing.brokerage.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Asset entities in the database.
 * Provides methods to retrieve assets owned by a customer.
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByCustomerId(String customerId);
    Optional<Asset> findByCustomerIdAndAssetName(String customerId, String assetName);
}