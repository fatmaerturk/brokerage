package com.ing.brokerage.controller;

import com.ing.brokerage.entity.Asset;
import com.ing.brokerage.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {
    private final AssetService assetService;
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }
    @GetMapping("/list")
    public ResponseEntity<List<Asset>> listAssets(@RequestParam String customerId) {
        List<Asset> assets = assetService.listAssets(customerId);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
}