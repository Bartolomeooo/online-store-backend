package com.example.onlinestorebackend.controller;

import com.example.onlinestorebackend.DTO.ReviewRequestDTO;
import com.example.onlinestorebackend.entity.Review;
import com.example.onlinestorebackend.entity.User;
import com.example.onlinestorebackend.service.ReviewService;
import com.example.onlinestorebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

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
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDTO reviewRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        reviewRequest.setUserId(user.getId());
        try {
            Review review = reviewService.createReview(reviewRequest);
            return ResponseEntity.ok(review);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
