package com.example.demo.model.Request;

import com.example.demo.eNum.ClubStatus;
import com.example.demo.model.LocalTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

     @JsonDeserialize(using = LocalTimeDeserializer.class)
     private String openingTime;

     @JsonDeserialize(using = LocalTimeDeserializer.class)
     private String closingTime;
     private String photo;
     private ClubStatus status;
     private String price;

     List<CourtSlotRequest> courts;
}
