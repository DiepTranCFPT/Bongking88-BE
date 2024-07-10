package com.example.demo.model.Request;

import com.example.demo.eNum.PromotionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PromotionRequest {
    private String Code;
    private double discount;
    private String startDate;
    private String endDate;
    @Enumerated(EnumType.STRING)
    private PromotionStatus status;
}
