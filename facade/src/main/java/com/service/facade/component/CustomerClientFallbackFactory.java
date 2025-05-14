package com.service.facade.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.facade.client.BackupCustomerClient;
import com.service.facade.client.CustomerClient;

import reactivefeign.FallbackFactory;

@Component
public class CustomerClientFallbackFactory implements FallbackFactory<CustomerClient> {
    @Autowired
    private BackupCustomerClient backupCustomerClient;

    @Override
    public CustomerClient apply(Throwable cause) {
        return id -> {
            System.out.println("Primary customer service is down. Redirecting to backup service...");
            return backupCustomerClient.getCustomer(id);
        };
    }

}
