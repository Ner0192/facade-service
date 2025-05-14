package com.service.facade.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.service.facade.dto.PaymentResponse;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

@ReactiveFeignClient(name = "backup-payment-service", url = "http://localhost:8092")
public interface BackupPaymentClient {

    @GetMapping("/payments/{customerId}")
    Flux<PaymentResponse> getPayments(@PathVariable("customerId") Long customerId);
}
