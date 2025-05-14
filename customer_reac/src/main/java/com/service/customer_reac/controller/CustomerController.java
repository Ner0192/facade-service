package com.service.customer_reac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.customer_reac.entity.Customer;
import com.service.customer_reac.service.CustomerService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired private CustomerService service;

    @GetMapping("/{id}")
    public Mono<Customer> getCustomer(@PathVariable Long id) {
        return service.getCustomerById(id);
    }
    @PostMapping
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        return service.createCustomer(customer);
    }
}
