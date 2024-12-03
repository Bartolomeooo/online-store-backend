package com.example.onlinestorebackend.controller;

import com.example.onlinestorebackend.DTO.ReviewRequestDTO;
import com.example.onlinestorebackend.entity.Review;
import com.example.onlinestorebackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id).orElse(null);
    }

    @GetMapping("/product/{productId}")
    public List<Review> getReviewByProductId(@PathVariable Long productId) {
        return reviewService.getReviewByProductId(productId);
    }

    @PostMapping
    public Review createReview(@RequestBody ReviewRequestDTO reviewRequest) {
        return reviewService.createReview(reviewRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
