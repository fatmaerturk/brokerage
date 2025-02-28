package com.ing.brokerage.repository;

import com.ing.brokerage.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Asset entities in the database.
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByCustomerId(Long customerId);
    Optional<Asset> findByCustomerIdAndAssetName(Long customerId, String assetName);
}
