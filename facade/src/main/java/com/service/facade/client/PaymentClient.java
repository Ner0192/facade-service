package com.service.facade.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.service.facade.component.PaymentClientFallbackFactory;
import com.service.facade.dto.PaymentResponse;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

@ReactiveFeignClient(name = "payment-service", url = "http://localhost:8082", fallbackFactory = PaymentClientFallbackFactory.class)
public interface PaymentClient {

    @GetMapping("/payments/{customerId}")
    Flux<PaymentResponse> getPayments(@PathVariable("customerId") Long customerId);
}
