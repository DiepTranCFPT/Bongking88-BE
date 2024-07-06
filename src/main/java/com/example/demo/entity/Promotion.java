package com.example.demo.entity;

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

    LocalDateTime startDate;

    LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;


}
