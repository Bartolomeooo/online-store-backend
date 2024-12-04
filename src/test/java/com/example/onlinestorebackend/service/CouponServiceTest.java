package com.example.onlinestorebackend.service;

import com.example.onlinestorebackend.entity.Coupon;
import com.example.onlinestorebackend.entity.CouponUsage;
import com.example.onlinestorebackend.entity.User;
import com.example.onlinestorebackend.repository.CouponRepository;
import com.example.onlinestorebackend.repository.CouponUsageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponUsageRepository couponUsageRepository;

    @InjectMocks
    private CouponService couponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCoupon() {
        Coupon coupon = new Coupon();
        coupon.setCode("DISCOUNT10");

        when(couponRepository.save(coupon)).thenReturn(coupon);

        Coupon createdCoupon = couponService.createCoupon(coupon);

        assertNotNull(createdCoupon);
        assertEquals("DISCOUNT10", createdCoupon.getCode());
        verify(couponRepository, times(1)).save(coupon);
    }

    @Test
    void testGetCouponByCode() {
        Coupon coupon = new Coupon();
        coupon.setCode("DISCOUNT10");

        when(couponRepository.findByCode("DISCOUNT10")).thenReturn(Optional.of(coupon));

        Optional<Coupon> foundCoupon = couponService.getCouponByCode("DISCOUNT10");

        assertTrue(foundCoupon.isPresent());
        assertEquals("DISCOUNT10", foundCoupon.get().getCode());
        verify(couponRepository, times(1)).findByCode("DISCOUNT10");
    }

    @Test
    void testCanUserUseCoupon_NewUsage() {
        User user = new User();
        user.setId(1L);

        Coupon coupon = new Coupon();
        coupon.setCode("DISCOUNT10");
        coupon.setUsageLimit(5);

        when(couponUsageRepository.findByUserAndCoupon(user, coupon)).thenReturn(Optional.empty());

        boolean canUse = couponService.canUserUseCoupon(user, coupon);

        assertTrue(canUse);
        verify(couponUsageRepository, times(1)).findByUserAndCoupon(user, coupon);
        verify(couponUsageRepository, times(1)).save(any(CouponUsage.class));
    }

    @Test
    void testCanUserUseCoupon_ExistingUsage_WithinLimit() {
        User user = new User();
        user.setId(1L);

        Coupon coupon = new Coupon();
        coupon.setCode("DISCOUNT10");
        coupon.setUsageLimit(5);

        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setUsageCount(3);

        when(couponUsageRepository.findByUserAndCoupon(user, coupon)).thenReturn(Optional.of(couponUsage));

        boolean canUse = couponService.canUserUseCoupon(user, coupon);

        assertTrue(canUse);
        verify(couponUsageRepository, times(1)).findByUserAndCoupon(user, coupon);
    }

    @Test
    void testCanUserUseCoupon_ExistingUsage_ExceedsLimit() {
        User user = new User();
        user.setId(1L);

        Coupon coupon = new Coupon();
        coupon.setCode("DISCOUNT10");
        coupon.setUsageLimit(3);

        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setUsageCount(4);

        when(couponUsageRepository.findByUserAndCoupon(user, coupon)).thenReturn(Optional.of(couponUsage));

        boolean canUse = couponService.canUserUseCoupon(user, coupon);

        assertFalse(canUse);
        verify(couponUsageRepository, times(1)).findByUserAndCoupon(user, coupon);
    }

    @Test
    void testIncrementCouponUsage_NewUsage() {
        User user = new User();
        user.setId(1L);

        Coupon coupon = new Coupon();
        coupon.setCode("DISCOUNT10");

        when(couponUsageRepository.findByUserAndCoupon(user, coupon)).thenReturn(Optional.empty());

        couponService.incrementCouponUsage(user, coupon);

        verify(couponUsageRepository, times(1)).findByUserAndCoupon(user, coupon);
        verify(couponUsageRepository, times(1)).save(any(CouponUsage.class));
    }

    @Test
    void testIncrementCouponUsage_ExistingUsage() {
        User user = new User();
        user.setId(1L);

        Coupon coupon = new Coupon();
        coupon.setCode("DISCOUNT10");

        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setUsageCount(2);

        when(couponUsageRepository.findByUserAndCoupon(user, coupon)).thenReturn(Optional.of(couponUsage));

        couponService.incrementCouponUsage(user, coupon);

        assertEquals(3, couponUsage.getUsageCount());
        verify(couponUsageRepository, times(1)).findByUserAndCoupon(user, coupon);
        verify(couponUsageRepository, times(1)).save(couponUsage);
    }

    @Test
    void testDeleteCoupon() {
        Long couponId = 1L;
        doNothing().when(couponRepository).deleteById(couponId);

        couponService.deleteCoupon(couponId);

        verify(couponRepository, times(1)).deleteById(couponId);
    }
}
