package com.example.demo.entity;

import com.example.demo.eNum.ClubStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;



@Entity
@Setter
@Getter
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private long locationId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "hotline")
    private String hotline;

    @Column(name = "opening_time")

    private String openTime;

    @Column(name = "closing_time")
    private String closeTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ClubStatus status;

    @Column(name = "price")
    private String price;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "location",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Court> courts;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Promotion> promotions;
}
