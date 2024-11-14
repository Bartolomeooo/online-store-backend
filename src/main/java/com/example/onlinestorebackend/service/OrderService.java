package com.example.onlinestorebackend.service;

import com.example.onlinestorebackend.DTO.ProductQuantityDTO;
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
    private ProductService productService;

    @Autowired
    private UserService userService;

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

    public Order createOrder(Long userId, List<ProductQuantityDTO> products) {
        Order order = new Order();
        User user = userService.getUserById(userId).get();
        order.setUser(user);
        order.setOrderDate(new Date());
        for (ProductQuantityDTO productDto : products) {
            Product product = productService.getProductById(productDto.getProductId());
            if (product == null) System.out.println("No product was found");
            order.addProduct(product, productDto.getQuantity());
        }

        return orderRepository.save(order);
    }
}

