package com.example.onlinestorebackend.controller;

import com.example.onlinestorebackend.DTO.OrderRequestDTO;
import com.example.onlinestorebackend.entity.Order;
import com.example.onlinestorebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id).orElse(null);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequestDTO orderRequest) {
        return orderService.createOrder(orderRequest.getUserId(), orderRequest.getProducts(), orderRequest.getCouponCode());
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
