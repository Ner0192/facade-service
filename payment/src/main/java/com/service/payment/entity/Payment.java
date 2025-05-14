package com.service.payment.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "payments" )
public class Payment {
    @Id
    private String id;
    private Long customerId;
    private Double amount;
    private String status;

    public Payment() {}

    public Payment(String id, Long customerId, Double amount, String status) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
