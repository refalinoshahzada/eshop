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
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        List<Product> allProduct = productRepository.findAll();
        return allProduct;
    }

    // Method to find the product by its ID, later used to edit and delete the products
    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId);
    }

    // Method to edit the product
    @Override
    public Product update(Product product) {
        return productRepository.update(product);
    }

    @Override
    public void deleteById(String productId) {
        productRepository.deleteById(productId);
    }
}