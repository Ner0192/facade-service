package com.service.facade.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.facade.client.BackupPaymentClient;
import com.service.facade.client.PaymentClient;

import reactivefeign.FallbackFactory;

@Component
public class PaymentClientFallbackFactory implements FallbackFactory<PaymentClient> {
    @Autowired
    private BackupPaymentClient backupPaymentClient;

    @Override
    public PaymentClient apply(Throwable cause) {
        return id -> {
            System.out.println("Primary payment service is down. Redirecting to backup service...");
            return backupPaymentClient.getPayments(id);
        };
    }
}
