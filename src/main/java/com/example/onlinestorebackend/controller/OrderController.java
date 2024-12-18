package com.example.onlinestorebackend.controller;

import com.example.onlinestorebackend.DTO.OrderDTO;
import com.example.onlinestorebackend.DTO.OrderRequestDTO;
import com.example.onlinestorebackend.entity.Order;
import com.example.onlinestorebackend.entity.User;
import com.example.onlinestorebackend.service.OrderService;
import com.example.onlinestorebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderService.getOrdersByUser(user.getId());

        List<OrderDTO> orderDTOs = orders.stream()
                .map(order -> new OrderDTO(
                        order.getId(),
                        order.getTotalPrice(),
                        order.getOrderDate()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            Order order = orderService.createOrder(
                    user.getId(),
                    orderRequest.getProducts(),
                    orderRequest.getCouponCode()
            );
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
