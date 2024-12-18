package com.example.onlinestorebackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date orderDate;

    @Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 0")
    private double totalPrice = 0.0;

    @Getter
    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = true)
    private Coupon coupon;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void addProduct(Product product, int quantity) {
        OrderProduct orderProduct = new OrderProduct(this, product, quantity);
        orderProducts.add(orderProduct);
        totalPrice += product.getPrice() * quantity;
    }

    public void calculateTotalPriceWithCoupon() {
        totalPrice *= (1 - coupon.getDiscount() / 100);
    }

    public Long getId() {
        return id;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public void setId(long id) {
        this.id = id;
    }
}
