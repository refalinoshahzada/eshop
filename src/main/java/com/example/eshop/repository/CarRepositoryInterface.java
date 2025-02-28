package com.example.eshop.repository;

import com.example.eshop.model.Car;
import java.util.Iterator;

public interface CarRepositoryInterface {
    Car create(Car car);
    Iterator<Car> findAll();
    Car findById(String carId);
    Car update(String carId, Car car);
    void delete(String carId);
}