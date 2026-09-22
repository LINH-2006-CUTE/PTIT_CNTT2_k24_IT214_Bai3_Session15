package com.example.bai3_it214_ss15.controller;

import com.example.bai3_it214_ss15.dto.OrderEvent;
import com.example.bai3_it214_ss15.service.OrderProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderProducerService orderProducerService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderEvent order) {
        orderProducerService.sendOrderEvent(order);
        return ResponseEntity.ok("Order created and event sent to Kafka successfully!");
    }
}
