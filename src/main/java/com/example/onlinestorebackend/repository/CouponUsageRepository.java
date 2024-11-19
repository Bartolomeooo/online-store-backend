package com.example.onlinestorebackend.repository;

import com.example.onlinestorebackend.entity.CouponUsage;
import com.example.onlinestorebackend.entity.Coupon;
import com.example.onlinestorebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {
    Optional<CouponUsage> findByUserAndCoupon(User user, Coupon coupon);
}

