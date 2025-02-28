package com.example.eshop.service;

import com.example.eshop.model.Product;
import com.example.eshop.repository.ProductRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepositoryInterface productRepository;

    @Override
    public Product create(Product product) {
        // Validate product
        validateProduct(product);

        // Generate ID if not present
        if (product.getProductId() == null || product.getProductId().isEmpty()) {
            product.setProductId(UUID.randomUUID().toString());
        }

        productRepository.create(product);
        return product;
    }

    private void validateProduct(Product product) {
        List<String> errors = new ArrayList<>();

        // Check for null product
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        // Validate name
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            errors.add("Product name is required");
        }

        // Validate quantity
        if (product.getProductQuantity() < 0) {
            errors.add("Product quantity cannot be negative");
        }

        // If there are any validation errors, throw exception
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product update(Product product) {
        validateProduct(product);
        return productRepository.update(product);
    }

    @Override
    public void deleteById(String productId) {
        productRepository.deleteById(productId);
    }
}