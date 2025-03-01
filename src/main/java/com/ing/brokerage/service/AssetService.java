package com.ing.brokerage.service;

import com.ing.brokerage.model.Asset;
import com.ing.brokerage.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class responsible for managing customer assets.
 * This includes retrieving a list of assets for a customer.
 */
@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;
    /**
     * Retrieves a list of assets owned by a specific customer.
     * @param customerId The ID of the customer.
     * @return List of assets.
     */
    public List<Asset> listAssets(Long customerId) {
        return assetRepository.findByCustomerId(customerId);
    }
}

