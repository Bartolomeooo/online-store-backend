package com.example.onlinestorebackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class OrderProduct {
    @EmbeddedId
    private OrderProductId id = new OrderProductId();

    @ManyToOne
    @MapsId("orderId")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    private int quantity;

    public OrderProduct() {}

    public OrderProduct(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.id = new OrderProductId(order.getId(), product.getId());
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Order getOrder() {
        return order;
    }
}

