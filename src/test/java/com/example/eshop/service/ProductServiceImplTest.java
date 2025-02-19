package com.example.eshop.service;

import com.example.eshop.model.Product;
import com.example.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
    void testCreate_withNoId_generatesNewId() {
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);
        assertNotNull(result.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testCreate_withExistingId_doesNotGenerateNewId() {
        String existingId = UUID.randomUUID().toString();
        product.setProductId(existingId);

        productService.create(product);
        assertEquals(existingId, product.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testCreate_withNullProduct_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> productService.create(null));
    }

    @Test
    void testCreate_withEmptyName_throwsException() {
        product.setProductName("");
        assertThrows(IllegalArgumentException.class, () -> productService.create(product));
    }

    @Test
    void testCreate_withNegativeQuantity_throwsException() {
        product.setProductQuantity(-1);
        assertThrows(IllegalArgumentException.class, () -> productService.create(product));
    }

    @Test
    void testCreate_withValidProduct_returnsCreatedProduct() {
        product.setProductId("fixed-id");
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);
        assertEquals("fixed-id", result.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testCreate_withNullId_generatesNewId() {
        product.setProductId(null);
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);
        assertNotNull(result.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testCreate_withEmptyId_generatesNewId() {
        product.setProductId("");
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);
        assertNotNull(result.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testFindAll_emptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyIterator());
        List<Product> results = productService.findAll();
        assertTrue(results.isEmpty());
        verify(productRepository).findAll();
    }

    @Test
    void testFindAll_withProducts() {
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
    void testFindById_found() {
        product.setProductId("some-id");
        when(productRepository.findById("some-id")).thenReturn(product);

        Product result = productService.findById("some-id");
        assertNotNull(result);
        assertEquals("some-id", result.getProductId());
        verify(productRepository).findById("some-id");
    }

    @Test
    void testFindById_notFound() {
        when(productRepository.findById("unknown-id")).thenReturn(null);

        Product result = productService.findById("unknown-id");
        assertNull(result);
        verify(productRepository).findById("unknown-id");
    }

    @Test
    void testUpdate_validProduct() {
        product.setProductId("valid-id");
        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);
        assertNotNull(result);
        assertEquals("valid-id", result.getProductId());
        verify(productRepository).update(product);
    }

    @Test
    void testUpdate_invalidProduct_throwsException() {
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