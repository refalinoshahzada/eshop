package com.example.eshop.service;

import com.example.eshop.model.Payment;
import com.example.eshop.model.Order;
import com.example.eshop.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {return null;}

    @Override
    public Payment setStatus(Payment payment, String status) {return null;}

    @Override
    public Payment getPayment(String paymentId) {return null;}

    @Override
    public List<Payment> getAllPayments() {return null;}
}