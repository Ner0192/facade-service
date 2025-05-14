package com.service.customer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.service.customer.entity.Customer;

public interface CustomerRepository extends MongoRepository<Customer, Long> {

    Customer findTopByOrderByIdDesc();

}
