package com.example.onlinestorebackend.repository;

import com.example.onlinestorebackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
