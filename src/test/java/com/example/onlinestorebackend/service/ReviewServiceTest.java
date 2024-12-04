package com.example.onlinestorebackend.service;

import com.example.onlinestorebackend.DTO.ReviewRequestDTO;
import com.example.onlinestorebackend.entity.Product;
import com.example.onlinestorebackend.entity.Review;
import com.example.onlinestorebackend.entity.User;
import com.example.onlinestorebackend.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReviews() {
        Review review1 = new Review();
        review1.setId(1L);
        review1.setContent("Great product!");

        Review review2 = new Review();
        review2.setId(2L);
        review2.setContent("Not bad.");

        when(reviewRepository.findAll()).thenReturn(List.of(review1, review2));

        List<Review> reviews = reviewService.getAllReviews();

        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testGetReviewById() {
        Review review = new Review();
        review.setId(1L);
        review.setContent("Great product!");

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Optional<Review> result = reviewService.getReviewById(1L);

        assertTrue(result.isPresent());
        assertEquals("Great product!", result.get().getContent());
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateReview() {
        ReviewRequestDTO reviewRequest = new ReviewRequestDTO();
        reviewRequest.setUserId(1L);
        reviewRequest.setProductId(1L);
        reviewRequest.setContent("Amazing!");
        reviewRequest.setRating(5);

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        Review review = new Review();
        review.setId(1L);
        review.setContent("Amazing!");
        review.setRating(5);
        review.setUser(user);
        review.setProduct(product);

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(productService.getProductById(1L)).thenReturn(product);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review createdReview = reviewService.createReview(reviewRequest);

        assertNotNull(createdReview);
        assertEquals("Amazing!", createdReview.getContent());
        assertEquals(5, createdReview.getRating());
        assertEquals(user, createdReview.getUser());
        assertEquals(product, createdReview.getProduct());
        verify(userService, times(1)).getUserById(1L);
        verify(productService, times(1)).getProductById(1L);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testGetReviewByProductId() {
        Review review = new Review();
        review.setId(1L);
        review.setContent("Great product!");

        when(reviewRepository.findByProductId(1L)).thenReturn(List.of(review));

        List<Review> reviews = reviewService.getReviewByProductId(1L);

        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals("Great product!", reviews.get(0).getContent());
        verify(reviewRepository, times(1)).findByProductId(1L);
    }

    @Test
    void testDeleteReview() {
        Long reviewId = 1L;

        doNothing().when(reviewRepository).deleteById(reviewId);

        reviewService.deleteReview(reviewId);

        verify(reviewRepository, times(1)).deleteById(reviewId);
    }
}
