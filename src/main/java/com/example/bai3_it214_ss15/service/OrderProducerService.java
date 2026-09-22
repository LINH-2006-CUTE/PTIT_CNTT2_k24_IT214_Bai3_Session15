package com.example.bai3_it214_ss15.service;

import com.example.bai3_it214_ss15.dto.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendOrderEvent(OrderEvent orderEvent) {
        try {
            if (orderEvent.getStatus() == null || orderEvent.getStatus().isEmpty()) {
                orderEvent.setStatus("PENDING");
            }
            String message = objectMapper.writeValueAsString(orderEvent);
            kafkaTemplate.send("order-events", message);
            System.out.println(" Gui order: " + orderEvent);
        } catch (Exception e) {
            System.err.println("Loi gui event: " + e.getMessage());
        }
    }
}
