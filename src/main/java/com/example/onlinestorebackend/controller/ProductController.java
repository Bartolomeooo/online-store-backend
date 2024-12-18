package com.example.onlinestorebackend.controller;

import com.example.onlinestorebackend.entity.Product;
import com.example.onlinestorebackend.repository.CategoryRepository;
import com.example.onlinestorebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/by-category")
    public List<Product> getProductsByCategory(@RequestParam Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        if (product.getCategories() != null) {
            product.setCategories(
                    product.getCategories().stream()
                            .map(category -> categoryRepository.findById(category.getId())
                                    .orElseThrow(() -> new RuntimeException("Category not found")))
                            .collect(Collectors.toList())
            );
        }
        return productService.createProduct(product);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
}

