package com.service.facade.dto;

public class PaymentResponse {
    private String id;
    private Double amount;
    private String status;

    public PaymentResponse() {
    }

    public PaymentResponse(String id, Long customerId, Double amount, String status) {
        this.id = "TXN-" + id;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = "TXN-" + id;
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
