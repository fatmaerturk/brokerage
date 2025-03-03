package com.ing.brokerage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assetName;
    private String orderSide;
    private int size;
    private double price;

    @ManyToOne
    private Customer customer;

    public Order(Customer customer, String assetName, String orderSide, int size, double price) {
        this.customer = customer;
        this.assetName = assetName;
        this.orderSide = orderSide;
        this.size = size;
        this.price = price;
    }
}