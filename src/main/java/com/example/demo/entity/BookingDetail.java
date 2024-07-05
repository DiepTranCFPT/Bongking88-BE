package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "booking_details")
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_details_id")
    private long BookingDetailsId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "playingDate")
    private Date PlayingDate;

    @Column(name = "price")
    private float Price;

    @Column(name = "status")
    private boolean Status;

    @OneToMany(mappedBy = "bookingDetail", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CourtSlot> courtSlots;
}
