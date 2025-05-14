package com.service.payment_reac.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.service.payment_reac.entity.Payment;

import reactor.core.publisher.Flux;

public interface PaymentRepository extends ReactiveMongoRepository<Payment,String> {
    Flux<Payment> findByCustomerId(Long customerId);
}
