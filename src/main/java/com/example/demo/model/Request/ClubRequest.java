package com.example.demo.model.Request;

import com.example.demo.eNum.ClubStatus;
import com.example.demo.model.LocalTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class ClubRequest {
     private String name;
     private String description;
     private String address;
     private String hotline;
     private int openingTime;
     private int closingTime;
     private String photo;
     private double priceSlot;
     private long ownerId;


}
