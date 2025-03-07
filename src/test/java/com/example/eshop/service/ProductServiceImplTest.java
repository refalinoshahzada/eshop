package com.example.eshop.service;

import com.example.eshop.model.Product;
import com.example.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductName("Valid Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateWithNullIdGeneratesNewId() {
        product.setProductId(null);

        when(productRepository.create(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            if (p.getProductId() == null || p.getProductId().isEmpty()) {
                p.setProductId(UUID.randomUUID().toString());
            }
            return p;
        });

        Product result = productService.create(product);
        assertNotNull(result.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testCreateWithNoIdGeneratesNewId() {
        when(productRepository.create(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            if (p.getProductId() == null || p.getProductId().isEmpty()) {
                p.setProductId(UUID.randomUUID().toString());
            }
            return p;
        });

        Product result = productService.create(product);
        assertNotNull(result.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testCreateWithExistingIdDoesNotGenerateNewId() {
        String existingId = UUID.randomUUID().toString();
        product.setProductId(existingId);

        when(productRepository.create(any(Product.class))).thenReturn(product);

        productService.create(product);
        assertEquals(existingId, product.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testCreateWithNullProductThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> productService.create(null));
    }

    @Test
    void testCreateWithEmptyNameThrowsException() {
        product.setProductName("");
        assertThrows(IllegalArgumentException.class, () -> productService.create(product));
    }

    @Test
    void testCreateWithNegativeQuantityThrowsException() {
        product.setProductQuantity(-1);
        assertThrows(IllegalArgumentException.class, () -> productService.create(product));
    }

    @Test
    void testCreateWithValidProductReturnsCreatedProduct() {
        product.setProductId("fixed-id");
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);
        assertEquals("fixed-id", result.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testFindAllEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> results = productService.findAll();
        assertTrue(results.isEmpty());
        verify(productRepository).findAll();
    }

    @Test
    void testFindAllWithProducts() {
        Product another = new Product();
        another.setProductId("another-id");
        another.setProductName("Another Product");
        another.setProductQuantity(5);

        List<Product> productList = Arrays.asList(product, another);
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> results = productService.findAll();
        assertEquals(2, results.size());
        verify(productRepository).findAll();
    }

    @Test
    void testFindByIdFound() {
        product.setProductId("some-id");
        when(productRepository.findById("some-id")).thenReturn(product);

        Product result = productService.findById("some-id");
        assertNotNull(result);
        assertEquals("some-id", result.getProductId());
        verify(productRepository).findById("some-id");
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById("unknown-id")).thenReturn(null);

        Product result = productService.findById("unknown-id");
        assertNull(result);
        verify(productRepository).findById("unknown-id");
    }

    @Test
    void testUpdateValidProduct() {
        product.setProductId("valid-id");
        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);
        assertNotNull(result);
        assertEquals("valid-id", result.getProductId());
        verify(productRepository).update(product);
    }

    @Test
    void testUpdateInvalidProductThrowsException() {
        // Missing product name triggers validation error
        Product invalidProduct = new Product();
        invalidProduct.setProductQuantity(5);

        assertThrows(IllegalArgumentException.class, () -> productService.update(invalidProduct));
        verify(productRepository, never()).update(any());
    }

    @Test
    void testDeleteById() {
        productService.deleteById("some-id");
        verify(productRepository).deleteById("some-id");
    }
}