package com.example.bai3_it214_ss15.service;

import com.example.bai3_it214_ss15.dto.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "order-events", groupId = "order-group")
    public void consumeOrder(String message) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(message, OrderEvent.class);
            System.out.println("[PaymentService] Received order event for orderId: " + orderEvent.getOrderId());
            orderEvent.setStatus("PAID");
            System.out.println("[PaymentService] Processing payment... Payment successful. Updated status: " + orderEvent.getStatus());
            String paymentJson = objectMapper.writeValueAsString(orderEvent);
            kafkaTemplate.send("payment-events", paymentJson);
            System.out.println("[PaymentService] Sent payment event for orderId: " + orderEvent.getOrderId());
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
        }
    }
}
