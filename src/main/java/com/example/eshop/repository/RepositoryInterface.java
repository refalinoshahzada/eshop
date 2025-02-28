package com.example.eshop.repository;

import java.util.List;

public interface RepositoryInterface<T> {
    T create(T item);
    List<T> findAll();
    T findById(String id);
    T update(T item);
    void deleteById(String id);
}