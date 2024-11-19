package com.example.onlinestorebackend.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderProductId implements Serializable {
    private Long orderId;
    private Long productId;

    public OrderProductId() {}

    public OrderProductId(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
