package com.example.demo.model.Response;


import com.example.demo.entity.CourtSlot;
import com.example.demo.entity.Location;
import lombok.Data;

@Data
public class CourtSlotResponse extends CourtSlot {

    Location location;

}
