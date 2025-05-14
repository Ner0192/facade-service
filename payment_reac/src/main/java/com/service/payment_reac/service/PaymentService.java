package com.service.payment_reac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.payment_reac.entity.Payment;
import com.service.payment_reac.repository.PaymentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Flux<Payment> getPaymentsByCustomerId(Long customerId) {
        return paymentRepository.findByCustomerId(customerId);

    }

    public Mono<Payment> createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
