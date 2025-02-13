package com.example.eshop.service;

import com.example.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public List<Product> findAll();
    Product findById(String productId);
    Product update(Product product);
}
