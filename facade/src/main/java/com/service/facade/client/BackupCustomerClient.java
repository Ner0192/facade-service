package com.service.facade.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.service.facade.dto.CustomerResponse;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "backup-customer-service", url = "http://localhost:8091")
public interface BackupCustomerClient {

    @GetMapping("/customers/{id}")
    Mono<CustomerResponse> getCustomer(@PathVariable Long id);
}
