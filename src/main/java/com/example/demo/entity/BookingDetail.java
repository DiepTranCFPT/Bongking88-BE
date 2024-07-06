package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private Booking booking;

    private float Price;

    @OneToOne
    @JoinColumn(name = "courtSlot_id")
    private CourtSlot courtSlot;
}
