package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Slot")
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SLOT_ID")
    private Long SlotId;

    @Column(name = "time")
    private Integer time;

    @Column(name = "price")
    private Double price;

    @OneToMany(mappedBy = "Slot",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CourtSlot> courtSlots;
}
