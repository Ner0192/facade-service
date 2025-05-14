package com.service.payment.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.service.payment.entity.Payment;
import com.service.payment.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping("/{customerId}")
    public List<Payment> getPayments(@PathVariable Long customerId) {
        return service.getPaymentsByCustomerId(customerId);
    }

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return service.createPayment(payment);
    }
}