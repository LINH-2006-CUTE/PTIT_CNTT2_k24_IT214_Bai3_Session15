package com.example.bai3_it214_ss15.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String orderId;
    private String productName;
    private Double amount;
    private String customerEmail;
    private String status;
}
