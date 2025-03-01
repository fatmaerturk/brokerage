package com.ing.brokerage.controller;

import com.ing.brokerage.model.Asset;
import com.ing.brokerage.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for handling asset-related operations.
 */
@RestController
@RequestMapping("/api/assets")
public class AssetController {
    @Autowired
    private AssetService assetService;
    /**
     * Endpoint to retrieve a list of assets owned by a customer.
     * @param customerId The ID of the customer.
     * @return List of assets.
     */
    @GetMapping("/list/{customerId}")
    public ResponseEntity<List<Asset>> listAssets(@PathVariable Long customerId) {
        return ResponseEntity.ok(assetService.listAssets(customerId));
    }
}
