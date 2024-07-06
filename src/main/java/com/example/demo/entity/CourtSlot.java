package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "court_slots")
public class CourtSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_SLOT_ID")
    long COURSE_SLOT_ID;


    @OneToMany(mappedBy = "courtSlots", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Slot> slot;

    @ManyToOne
    @JoinColumn(name = "BookingDetailsId")
    BookingDetail bookingDetail;

    @ManyToOne
    @JoinColumn(name = "Location_ID")
    Location location;

}
