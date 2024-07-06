package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    @OneToMany(mappedBy = "court")
    private List<CourtSlot> courtSlots;



}
