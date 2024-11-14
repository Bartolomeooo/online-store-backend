package com.example.onlinestorebackend.DTO;

public class ReviewRequestDTO {
    private Long userId;
    private Long productId;
    private String content;
    private int rating;

    public Long getProductId() {
        return productId;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
