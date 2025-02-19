package com.example.eshop.repository;

import com.example.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Clear the repository before each test
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(savedProduct.getProductId(), product.getProductId());
        assertEquals(savedProduct.getProductName(), product.getProductName());
        assertEquals(savedProduct.getProductQuantity(), product.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de45-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdReturnsNullIfNotFound() {
        // No products created or product with different ID
        Product result = productRepository.findById("missing-id");
        assertNull(result, "Expected null if product does not exist");
    }

    @Test
    void testFindByIdReturnsProductIfFound() {
        Product product = new Product();
        product.setProductId("found-id");
        product.setProductName("Found Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product result = productRepository.findById("found-id");
        assertNotNull(result, "Should return the product if found");
        assertEquals("Found Product", result.getProductName());
    }

    @Test
    void testFindByIdWithMultipleProducts() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product result1 = productRepository.findById("id-1");
        assertNotNull(result1, "Should return the product if found");
        assertEquals("Product 1", result1.getProductName());

        Product result2 = productRepository.findById("id-2");
        assertNotNull(result2, "Should return the product if found");
        assertEquals("Product 2", result2.getProductName());
    }

    @Test
    void testUpdateReturnsNullIfNotFound() {
        Product product = new Product();
        product.setProductId("non-existent-id");
        product.setProductName("Will Not Update");
        product.setProductQuantity(42);

        // Attempt to update non-existent product
        Product result = productRepository.update(product);
        assertNull(result, "Should return null if product is not found for update");
    }

    @Test
    void testUpdateReplacesExistingProduct() {
        // Create a product
        Product product = new Product();
        product.setProductId("existing-id");
        product.setProductName("Old Name");
        product.setProductQuantity(5);
        productRepository.create(product);

        // Update the existing product
        Product updatedProduct = new Product();
        updatedProduct.setProductId("existing-id");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(99);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result, "Should return the updated product if found");
        assertEquals("New Name", result.getProductName());
        assertEquals(99, result.getProductQuantity());
    }

    @Test
    void testUpdateWithMultipleProducts() {
        // Create multiple products
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        // Update the second product
        Product updatedProduct = new Product();
        updatedProduct.setProductId("id-2");
        updatedProduct.setProductName("Updated Product 2");
        updatedProduct.setProductQuantity(30);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result, "Should return the updated product if found");
        assertEquals("Updated Product 2", result.getProductName());
        assertEquals(30, result.getProductQuantity());

        // Verify the first product remains unchanged
        Product unchangedProduct = productRepository.findById("id-1");
        assertNotNull(unchangedProduct, "Should return the first product");
        assertEquals("Product 1", unchangedProduct.getProductName());
        assertEquals(10, unchangedProduct.getProductQuantity());
    }

    @Test
    void testDeleteByIdSucceedsIfProductExists() {
        // Create a product
        Product product = new Product();
        product.setProductId("delete-id");
        product.setProductName("Deletable Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        // Now delete it
        productRepository.deleteById("delete-id");

        // Verify it's gone
        assertNull(productRepository.findById("delete-id"), "Expected product to be deleted");
    }

    @Test
    void testDeleteByIdDoesNothingIfProductNotFound() {
        // Delete a non-existent product
        productRepository.deleteById("missing-id");

        // Repository should remain empty
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext(), "Expected an empty repository if product was never there");
    }
}