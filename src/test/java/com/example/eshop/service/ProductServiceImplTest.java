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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void testCreateWithNoIdGeneratesNewId() {
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);
        assertNotNull(result.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testCreateWithExistingIdDoesNotGenerateNewId() {
        String existingId = UUID.randomUUID().toString();
        product.setProductId(existingId);

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
    void testCreateWithNullIdGeneratesNewId() {
        product.setProductId(null);
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);
        assertNotNull(result.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testCreateWithEmptyIdGeneratesNewId() {
        product.setProductId("");
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);
        assertNotNull(result.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testFindAllEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyIterator());
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

        Iterator<Product> iterator = Arrays.asList(product, another).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

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