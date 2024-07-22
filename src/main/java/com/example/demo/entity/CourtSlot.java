package com.example.demo.entity;

import com.example.demo.eNum.CourtSlotStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourtSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String date;

    @Enumerated(EnumType.STRING)
    CourtSlotStatus status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookingDetail_id")
    @JsonIgnore
    BookingDetail bookingDetail;

    @JoinColumn(name = "codebooking")
    private String codebooking;
}
