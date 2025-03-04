package com.ing.brokerage.controller;

import com.ing.brokerage.entity.Asset;
import com.ing.brokerage.service.AssetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AssetControllerTest {

    private AssetController assetController;
    private AssetService assetService;

    @BeforeEach
    public void setUp() {
        assetService = mock(AssetService.class);
        assetController = new AssetController(assetService);

        // Set up SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
        UserDetails userDetails = User.withUsername("customer1").password("password").authorities("ROLE_USER").build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    @WithMockUser(username = "customer1", roles = {"USER"})
    public void testListAssets() {
        List<Asset> mockAssets = Arrays.asList(new Asset(), new Asset());
        when(assetService.listAssets("customer1")).thenReturn(mockAssets);

        ResponseEntity<List<Asset>> response = assetController.listAssets();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAssets, response.getBody());
    }
}