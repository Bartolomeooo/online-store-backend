package com.example.onlinestorebackend.service;

import com.example.onlinestorebackend.DTO.ReviewRequestDTO;
import com.example.onlinestorebackend.entity.Product;
import com.example.onlinestorebackend.entity.Review;
import com.example.onlinestorebackend.entity.User;
import com.example.onlinestorebackend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(ReviewRequestDTO reviewRequest) {
        User user = userService.getUserById(reviewRequest.getUserId()).get();
        Product product = productService.getProductById(reviewRequest.getProductId());

        Review review = new Review();
        review.setContent(reviewRequest.getContent());
        review.setRating(reviewRequest.getRating());
        review.setUser(user);
        review.setProduct(product);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewByProductId(Long userId) {
        return reviewRepository.findByProductId(userId);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
