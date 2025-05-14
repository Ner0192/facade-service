package com.service.payment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.service.payment.entity.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findByCustomerId(Long customerId);
}
