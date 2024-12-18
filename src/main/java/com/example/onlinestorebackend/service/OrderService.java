package com.example.onlinestorebackend.service;

import com.example.onlinestorebackend.DTO.ProductQuantityDTO;
import com.example.onlinestorebackend.entity.Coupon;
import com.example.onlinestorebackend.entity.Order;
import com.example.onlinestorebackend.entity.Product;
import com.example.onlinestorebackend.entity.User;
import com.example.onlinestorebackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order createOrder(Long userId, List<ProductQuantityDTO> products, String couponCode) {
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());

        for (ProductQuantityDTO productDto : products) {
            Product product = productService.getProductById(productDto.getProductId());
            if (product == null) throw new RuntimeException("Product not found");
            order.addProduct(product, productDto.getQuantity());
        }

        if (couponCode != null) {
            Coupon coupon = couponService.getCouponByCode(couponCode)
                    .orElseThrow(() -> new RuntimeException("Invalid coupon code"));

            if (!couponService.canUserUseCoupon(user, coupon)) {
                throw new RuntimeException("Coupon usage limit exceeded for this user");
            }
            order.setCoupon(coupon);
            order.calculateTotalPriceWithCoupon();
            couponService.incrementCouponUsage(user, coupon);
        }

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
