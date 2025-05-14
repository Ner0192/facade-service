package com.service.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.service.customer.entity.Customer;
import com.service.customer.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repo;

    public Customer getCustomerById(Long id) {

        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

    }

    public Customer createCustomer(Customer customer) {
        Customer lastCustomer = repo.findTopByOrderByIdDesc();
        long nextId = (lastCustomer != null) ? lastCustomer.getId() + 1 : 1;
        customer.setId(nextId);
        return repo.save(customer);

    }
}
