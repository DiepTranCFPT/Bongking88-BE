package com.example.demo.entity;

import com.example.demo.eNum.PromotionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String  code;

    double  discount;

    String startDate;

    String endDate;


    @Enumerated(EnumType.STRING)
    PromotionStatus status;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;


}
