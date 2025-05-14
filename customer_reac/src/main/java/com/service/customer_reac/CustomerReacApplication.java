package com.service.customer_reac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.service.customer_reac", "com.service.agent"})
public class CustomerReacApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerReacApplication.class, args);
	}

}
