package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name ="Promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion")
    long promotionId;

    @Column(name = "code")
    String  code;

    @Column(name = "discount")
    double  discount;

    @Column(name = "StarDate")
    LocalDateTime startDate;

    @Column(name = "EndDate")
    LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

//    long locationId;

}
