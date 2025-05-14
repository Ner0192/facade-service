package com.service.payment_reac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.payment_reac.entity.Payment;
import com.service.payment_reac.service.PaymentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping("/{customerId}")
    public Flux<Payment> getPayments(@PathVariable Long customerId) {
        return service.getPaymentsByCustomerId(customerId);
    }

    @PostMapping
    public Mono<Payment> createPayment(@RequestBody Payment payment) {
        return service.createPayment(payment);
    }
}