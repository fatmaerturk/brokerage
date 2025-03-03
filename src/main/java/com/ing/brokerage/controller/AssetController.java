package com.ing.brokerage.controller;

import com.ing.brokerage.model.Asset;
import com.ing.brokerage.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for asset-related endpoints.
 */
@RestController
@RequestMapping("/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    /**
     * Endpoint to list all assets for a given customer.
     */
    @GetMapping
    public ResponseEntity<List<Asset>> listAssets(@RequestParam String customerId) {
        List<Asset> assets = assetService.listAssets(customerId);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
}