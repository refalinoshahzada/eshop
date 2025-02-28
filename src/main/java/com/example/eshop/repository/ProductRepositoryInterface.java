package com.example.eshop.repository;

import com.example.eshop.model.Product;
import java.util.Iterator;

public interface ProductRepositoryInterface {
    Product create(Product product);
    Iterator<Product> findAll();
    Product findById(String productId);
    Product update(Product product);
    void delete(String productId);
}