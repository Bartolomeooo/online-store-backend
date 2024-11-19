package com.example.onlinestorebackend.service;

import com.example.onlinestorebackend.entity.Coupon;
import com.example.onlinestorebackend.entity.CouponUsage;
import com.example.onlinestorebackend.entity.User;
import com.example.onlinestorebackend.repository.CouponRepository;
import com.example.onlinestorebackend.repository.CouponUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponUsageRepository couponUsageRepository;

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Optional<Coupon> getCouponByCode(String code) {
        return couponRepository.findByCode(code);
    }

    public boolean canUserUseCoupon(User user, Coupon coupon) {
        Optional<CouponUsage> couponUsageOpt = couponUsageRepository.findByUserAndCoupon(user, coupon);
        if (couponUsageOpt.isPresent()) {
            CouponUsage couponUsage = couponUsageOpt.get();
            return couponUsage.getUsageCount() < coupon.getUsageLimit();
        } else {
            createCouponUsage(user, coupon);
            return true;
        }
    }

    public void incrementCouponUsage(User user, Coupon coupon) {
        CouponUsage couponUsage = couponUsageRepository
                .findByUserAndCoupon(user, coupon)
                .orElse(new CouponUsage());

        couponUsage.setUser(user);
        couponUsage.setCoupon(coupon);
        couponUsage.setUsageCount(couponUsage.getUsageCount() + 1);
        couponUsageRepository.save(couponUsage);
    }

    public void createCouponUsage(User user, Coupon coupon) {
        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setUser(user);
        couponUsage.setCoupon(coupon);
        couponUsage.setUsageCount(0);
        couponUsageRepository.save(couponUsage);
    }

    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }
}
