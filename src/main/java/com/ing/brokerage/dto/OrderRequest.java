package com.ing.brokerage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private String customerId;
    private String assetName;
    private String orderSide;
    private int size;
    private double price;
}