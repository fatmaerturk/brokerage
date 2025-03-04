package com.ing.brokerage.repository;

import com.ing.brokerage.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByCustomerId(String customerId);
    Optional<Asset> findByCustomerIdAndAssetName(String customerId, String assetName);
}