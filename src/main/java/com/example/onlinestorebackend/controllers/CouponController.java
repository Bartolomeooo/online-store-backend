package com.example.onlinestorebackend.controllers;

import com.example.onlinestorebackend.entity.Coupon;
import com.example.onlinestorebackend.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping
    public Coupon createCoupon(@RequestBody Coupon coupon) {
        return couponService.createCoupon(coupon);
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
