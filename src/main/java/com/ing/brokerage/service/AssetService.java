package com.ing.brokerage.service;

import com.ing.brokerage.model.Asset;
import com.ing.brokerage.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for managing customer assets, including listing customer holdings.
 */
@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;
    public List<Asset> listAssets(Long customerId) {
        return assetRepository.findByCustomerId(customerId);
    }
}

