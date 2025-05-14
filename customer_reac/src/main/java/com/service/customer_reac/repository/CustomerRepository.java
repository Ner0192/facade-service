package com.service.customer_reac.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.service.customer_reac.entity.Customer;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, Long> { }
