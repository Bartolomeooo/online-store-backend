package com.example.onlinestorebackend.DTO;

import java.util.List;

public class OrderRequestDTO {
    private Long userId;
    private List<ProductQuantityDTO> products;

    public OrderRequestDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ProductQuantityDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductQuantityDTO> products) {
        this.products = products;
    }
}

