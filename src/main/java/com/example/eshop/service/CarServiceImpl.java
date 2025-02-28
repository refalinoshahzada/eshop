package com.example.eshop.service;

import com.example.eshop.model.Car;
import com.example.eshop.repository.CarRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepositoryInterface carRepository;

    @Override
    public Car create(Car car) {
        return carRepository.create(car);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findById(String carId) {
        return carRepository.findById(carId);
    }

    @Override
    public void update(String carId, Car car) {
        carRepository.update(car);
    }

    @Override
    public void deleteCarById(String carId) {
        carRepository.deleteById(carId);
    }
}