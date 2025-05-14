package com.service.facade.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.facade.dto.OrderResponse;
import com.service.facade.service.OrderFacadeService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderFacadeService orderFacadeService;

    public OrderController(OrderFacadeService orderFacadeService) {
        this.orderFacadeService = orderFacadeService;
    }

    @GetMapping("/{customerId}")
    public Mono<OrderResponse> getOrder(@PathVariable Long customerId) {
        return orderFacadeService.getOrderDetails(customerId);
    }
}

