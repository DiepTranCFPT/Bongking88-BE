package com.example.demo.controller;


import com.example.demo.entity.Court;
import com.example.demo.model.Request.CourtResquest;
import com.example.demo.model.Response.CourtResponse;
import com.example.demo.service.CourtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name="api")
@RequestMapping("api/court")
@CrossOrigin("*")
public class CourtAPI {

    @Autowired
    CourtService courtService;

    @GetMapping("/location/{id}")
    public ResponseEntity getCourtByLocation(@PathVariable long id){
        List<Court> courts = courtService.getCourtByLocation(id);
        return  ResponseEntity.ok(courts);
    }
    @GetMapping("{id}")
    public ResponseEntity getCourt(@PathVariable long id){
        CourtResponse court = courtService.getCourt(id);
        return  ResponseEntity.ok(court);
    }

    @PostMapping
    public ResponseEntity createCourt(@RequestBody CourtResquest courtResquest){
        Court court = courtService.createCourt(courtResquest);
        return  ResponseEntity.ok(court);
    }

    @PutMapping("{id}")
    public ResponseEntity updateCourt(@PathVariable long id,@RequestBody CourtResquest courtResquest){
        Court court = courtService.updateCourt(id,courtResquest);
        return  ResponseEntity.ok(court);
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteCourt(@PathVariable long id){
        Court court = courtService.deleteCourt(id);
        return  ResponseEntity.ok(court);
    }
}
