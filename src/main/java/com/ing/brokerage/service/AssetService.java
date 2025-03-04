package com.ing.brokerage.service;

import com.ing.brokerage.entity.Asset;
import com.ing.brokerage.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;
    public List<Asset> listAssets(String customerId) {
        return assetRepository.findByCustomerId(customerId);
    }
}