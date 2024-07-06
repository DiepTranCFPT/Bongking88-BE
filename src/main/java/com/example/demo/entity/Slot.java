package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String time;

    private double price;

    @OneToMany(mappedBy = "slot")
    @JsonIgnore
    private List<CourtSlot> courtSlots;

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;
}
