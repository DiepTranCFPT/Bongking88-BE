package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "court_slots")
public class CourtSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_SLOT_ID")
    long COURSE_SLOT_ID;

    @ManyToOne
    @JoinColumn(name = "SLOT_ID")
    Slot Slot;

    @ManyToOne
    @JoinColumn(name = "BookingDetailsId")
    BookingDetail bookingDetail;

    @ManyToOne
    @JoinColumn(name = "Location_ID")
    Location location;
}
