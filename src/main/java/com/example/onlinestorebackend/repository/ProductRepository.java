package com.example.onlinestorebackend.repository;

import com.example.onlinestorebackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoriesId(Long categoryId);
}
