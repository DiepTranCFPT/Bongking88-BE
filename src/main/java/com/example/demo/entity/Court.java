package com.example.demo.entity;


import com.example.demo.eNum.CourtStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    String image;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonIgnore
    Location location;

    @Enumerated(EnumType.STRING)
    CourtStatus status;

    @OneToMany(mappedBy = "court")
    @JsonIgnore
    private List<CourtSlot> courtSlots;



}
