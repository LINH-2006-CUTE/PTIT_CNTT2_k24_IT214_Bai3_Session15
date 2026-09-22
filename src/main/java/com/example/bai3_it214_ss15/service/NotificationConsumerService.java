package com.example.bai3_it214_ss15.service;

import com.example.bai3_it214_ss15.dto.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumerService {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "payment-events", groupId = "payment-group")
    public void consumePayment(String message) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(message, OrderEvent.class);
            System.out.println("[NotificationService] Received confirmation for order " + orderEvent.getOrderId() + ". Sending email to " + orderEvent.getCustomerEmail());
            System.out.println("[NotificationService] Email sent successfully!");
        } catch (Exception e) {
            System.err.println("Error processing notification: " + e.getMessage());
        }
    }
}
