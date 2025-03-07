package com.example.eshop.service;

import com.example.eshop.model.Product;
import com.example.eshop.repository.ProductRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepositoryInterface productRepository;

    @Override
    public Product create(Product product) {
        // Throw IllegalArgumentException if product is null
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        // Throw IllegalArgumentException if product name is empty
        if ((product.getProductName() == null) || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        // Throw IllegalArgumentException if quantity is less than 1
        if (product.getProductQuantity() < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product update(Product product) {
        // Perform the same validations on update:
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if ((product.getProductName() == null) || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getProductQuantity() < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        return productRepository.update(product);
    }

    @Override
    public void deleteById(String productId) {
        productRepository.deleteById(productId);
    }
}