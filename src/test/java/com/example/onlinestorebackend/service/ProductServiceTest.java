package com.example.onlinestorebackend.service;

import com.example.onlinestorebackend.entity.Category;
import com.example.onlinestorebackend.entity.Product;
import com.example.onlinestorebackend.repository.CategoryRepository;
import com.example.onlinestorebackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductsByCategory() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");

        when(productRepository.findByCategoriesId(1L)).thenReturn(List.of(product));

        List<Product> products = productService.getProductsByCategory(1L);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product 1", products.get(0).getName());
        verify(productRepository, times(1)).findByCategoriesId(1L);
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Product 1", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("New Product");

        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals("New Product", createdProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testUpdateProduct() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Product");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(10.0);
        existingProduct.setQuantity(5);

        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(20.0);
        updatedProduct.setQuantity(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(20.0, result.getPrice());
        assertEquals(10, result.getQuantity());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existingProduct);
    }
}
