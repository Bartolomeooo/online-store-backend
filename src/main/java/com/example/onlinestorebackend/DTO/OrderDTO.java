package com.example.onlinestorebackend.DTO;

import java.util.Date;

public class OrderDTO {
    private Long id;
    private Double total;
    private Date createdAt;

    public OrderDTO(Long id, Double total, Date createdAt) {
        this.id = id;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

