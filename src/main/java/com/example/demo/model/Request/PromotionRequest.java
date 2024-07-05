package com.example.demo.model.Request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PromotionRequest {
    private String Code;
    private double discount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
