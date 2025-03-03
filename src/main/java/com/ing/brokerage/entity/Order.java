package com.ing.brokerage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ing.brokerage.util.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@JsonIgnoreProperties("customer")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assetName;
    private String orderSide;
    private int size;
    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}