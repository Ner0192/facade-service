package com.service.customer_reac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.service.customer_reac.entity.Customer;
import com.service.customer_reac.repository.CustomerRepository;

import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repo;

    public Mono<Customer> getCustomerById(Long id) {

        return repo.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")));

    }

    public Mono<Customer> createCustomer(Customer customer) {

        return repo.findAll()
                .map(Customer::getId)
                .cast(Long.class)
                .defaultIfEmpty(0L)
                .reduce(Long::max)
                .map(id -> id + 1)
                .flatMap(id -> {
                    customer.setId(id);
                    return repo.save(customer);
                });
    }
}
