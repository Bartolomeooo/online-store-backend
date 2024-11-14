package com.example.onlinestorebackend.repository;

import com.example.onlinestorebackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
