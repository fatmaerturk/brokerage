package com.ing.brokerage.service;

import com.ing.brokerage.entity.Asset;
import com.ing.brokerage.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AssetServiceTest {

    private AssetService assetService;
    private AssetRepository assetRepository;

    @BeforeEach
    public void setUp() {
        assetRepository = mock(AssetRepository.class);
        assetService = new AssetService();
        assetService.assetRepository = assetRepository;
    }

    @Test
    public void testListAssets() {
        List<Asset> mockAssets = Arrays.asList(new Asset(), new Asset());
        when(assetRepository.findByCustomerId("customer1")).thenReturn(mockAssets);

        List<Asset> assets = assetService.listAssets("customer1");
        assertEquals(mockAssets, assets);
    }
}