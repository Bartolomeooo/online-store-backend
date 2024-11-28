package com.example.onlinestorebackend.service;

import com.example.onlinestorebackend.entity.Category;
import com.example.onlinestorebackend.entity.Product;
import com.example.onlinestorebackend.repository.CategoryRepository;
import com.example.onlinestorebackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoriesId(categoryId);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);

        existingProduct.get().setName(updatedProduct.getName());
        existingProduct.get().setDescription(updatedProduct.getDescription());
        existingProduct.get().setPrice(updatedProduct.getPrice());
        existingProduct.get().setQuantity(updatedProduct.getQuantity());

        return productRepository.save(existingProduct.get());
    }
}

