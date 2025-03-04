package com.ing.brokerage.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CustomerLoginRequest {
    private String customerId;
    private String password;
}