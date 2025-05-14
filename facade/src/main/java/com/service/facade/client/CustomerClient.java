package com.service.facade.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.service.facade.component.CustomerClientFallbackFactory;
import com.service.facade.dto.CustomerResponse;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "customer-service", url = "http://localhost:8081", fallbackFactory = CustomerClientFallbackFactory.class)
public interface CustomerClient {

    @GetMapping("/customers/{id}")
    Mono<CustomerResponse> getCustomer(@PathVariable("id") Long id);
}
