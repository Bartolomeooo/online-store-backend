package com.example.onlinestorebackend.controller;

import com.example.onlinestorebackend.entity.Coupon;
import com.example.onlinestorebackend.entity.User;
import com.example.onlinestorebackend.service.CouponService;
import com.example.onlinestorebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Coupon createCoupon(@RequestBody Coupon coupon) {
        return couponService.createCoupon(coupon);
    }
    @GetMapping("/validate")
    public ResponseEntity<?> validateCoupon(
            @RequestParam("code") String couponCode,
            @RequestParam("orderTotal") Double orderTotal
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Coupon> couponOpt = couponService.getCouponByCode(couponCode);
        if (couponOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("valid", false, "message", "Invalid coupon code."));
        }

        Coupon coupon = couponOpt.get();

        if (coupon.getExpirationDate() != null && coupon.getExpirationDate().before(new Date())) {
            return ResponseEntity.badRequest().body(Map.of("valid", false, "message", "Coupon has expired."));
        }

        if (coupon.getMinimumOrderValue() != null && orderTotal < coupon.getMinimumOrderValue()) {
            return ResponseEntity.badRequest().body(Map.of("valid", false, "message", "Order total is too low for this coupon."));
        }

        if (!couponService.canUserUseCoupon(user, coupon)) {
            return ResponseEntity.badRequest().body(Map.of("valid", false, "message", "Coupon usage limit exceeded."));
        }

        return ResponseEntity.ok(Map.of("valid", true, "discount", coupon.getDiscount()));
    }

    @GetMapping("/{code}")
    public Optional<Coupon> getCouponByCode(@PathVariable String code) {
        return couponService.getCouponByCode(code);
    }

    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
    }
}
