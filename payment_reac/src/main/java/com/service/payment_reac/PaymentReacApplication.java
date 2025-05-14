package com.service.payment_reac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.service.payment_reac", "com.service.agent"})
public class PaymentReacApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentReacApplication.class, args);
	}

}
