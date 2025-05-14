package com.service.facade.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "customerId", "name", "email", "payments" })
public class OrderResponse {
    long customerId;
    String name;
    String email;
    private List<PaymentResponse> payments;

    public OrderResponse(CustomerResponse customer, List<PaymentResponse> payments) {
        this.customerId = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.payments = payments;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PaymentResponse> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResponse> payments) {
        this.payments = payments;
    }
}