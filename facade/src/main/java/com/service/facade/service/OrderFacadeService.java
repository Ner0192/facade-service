package com.service.facade.service;

import org.springframework.stereotype.Service;

import com.service.facade.client.CustomerClient;
import com.service.facade.client.PaymentClient;
import com.service.facade.dto.OrderResponse;

import reactor.core.publisher.Mono;

@Service
public class OrderFacadeService {
        private final CustomerClient customerClient;
        private final PaymentClient paymentClient;

        public OrderFacadeService(CustomerClient customerClient, PaymentClient paymentClient) {
                this.customerClient = customerClient;
                this.paymentClient = paymentClient;
        }

        public Mono<OrderResponse> getOrderDetails(Long customerId) {
                return customerClient.getCustomer(customerId)
                                .zipWith(paymentClient.getPayments(customerId)
                                                .collectList(), OrderResponse::new);
        }

}
