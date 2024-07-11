package com.example.demo.controller;


import com.example.demo.entity.CourtSlot;
import com.example.demo.model.Response.CourtSlotResponse;
import com.example.demo.service.CourtSlotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name="api")
@RequestMapping("api/courtSlot")
@CrossOrigin("*")
public class CourtSlotAPI {


    @Autowired
    CourtSlotService courtSlotService;

    @GetMapping("/customer/{id}")
    public ResponseEntity getCourtSlotByCustomerId(@PathVariable  long id){
        List<CourtSlotResponse> list = courtSlotService.getCourtSlotByCustomerId(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/location/{id}")
    public ResponseEntity getCourtSlotBylocationId(@PathVariable  long id){
        List<CourtSlot> list = courtSlotService.getCourtSlotByLocation(id);
        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity updateCourtSlot(@PathVariable  long id){
        CourtSlot courtSlot = courtSlotService.updateCourtSlot(id);
        return ResponseEntity.ok(courtSlot);
    }




}
