package com.service.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.payment.entity.Payment;
import com.service.payment.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getPaymentsByCustomerId(Long customerId) {
        return paymentRepository.findByCustomerId(customerId);

    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
