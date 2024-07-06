package com.example.demo.model.Response;

import com.example.demo.eNum.CourtStatus;
import com.example.demo.entity.CourtSlot;
import com.example.demo.entity.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class CourtResponse {
    long id;
    String name;
    Location location;
    @Enumerated(EnumType.STRING)
    CourtStatus status;
    String image;
}
