package com.example.onlinestorebackend.DTO;

public class ProductQuantityDTO {
    private Long productId;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return productId;
    }
}
