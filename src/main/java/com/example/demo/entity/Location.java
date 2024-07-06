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
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private String address;

    private String hotline;

    private String openTime;

    private String closeTime;

    @Enumerated(EnumType.STRING)
    private ClubStatus status;

    private String photo;

    @OneToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Account owner;

    @OneToMany(mappedBy = "location",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Court> courts;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Promotion> promotions;
}
