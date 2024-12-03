package com.example.onlinestorebackend.controller;

import com.example.onlinestorebackend.entity.Category;
import com.example.onlinestorebackend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
