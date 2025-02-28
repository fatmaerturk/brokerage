package com.ing.brokerage.model;

import jakarta.persistence.*;

/**
 * Entity representing a customer's asset holdings.
 * Each asset has a total size and a usable size.
 */
@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String assetName;
    private int size;
    private int usableSize;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getUsableSize() {
        return usableSize;
    }

    public void setUsableSize(int usableSize) {
        this.usableSize = usableSize;
    }
}

