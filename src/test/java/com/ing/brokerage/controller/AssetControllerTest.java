package com.ing.brokerage.controller;

import com.ing.brokerage.entity.Asset;
import com.ing.brokerage.service.AssetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AssetControllerTest {

    @Mock
    private AssetService assetService;

    @InjectMocks
    private AssetController assetController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListAssets() {
        String customerId = "123";
        Asset asset1 = new Asset();
        asset1.setId(1L);
        asset1.setCustomerId(customerId);
        asset1.setAssetName("Asset 1");
        asset1.setTotalSize(100);
        asset1.setUsableSize(80);

        Asset asset2 = new Asset();
        asset2.setId(2L);
        asset2.setCustomerId(customerId);
        asset2.setAssetName("Asset 2");
        asset2.setTotalSize(200);
        asset2.setUsableSize(150);

        List<Asset> assets = Arrays.asList(asset1, asset2);

        when(assetService.listAssets(customerId)).thenReturn(assets);

        ResponseEntity<List<Asset>> response = assetController.listAssets(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(assets, response.getBody());
        verify(assetService, times(1)).listAssets(customerId);
    }
}