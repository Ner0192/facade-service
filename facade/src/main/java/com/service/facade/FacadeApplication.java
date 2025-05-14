package com.service.facade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication(scanBasePackages = {"com.service.facade", "com.service.agent"})
@EnableReactiveFeignClients
public class FacadeApplication {
    public static void main(String[] args) {
        SpringApplication.run(FacadeApplication.class, args);
    }
}