package com.example.demo.controller;

import com.example.demo.entity.Location;
import com.example.demo.model.Request.ClubRequest;
import com.example.demo.service.LocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name="api")
@CrossOrigin("*")

public class LocationAPI {
    @Autowired
    private LocationService locationService;

    @GetMapping("/getAllClub")
    public ResponseEntity<List<Location>> getAllClubs() {

        List<Location> clubs = locationService.getAllClubs();
        if (clubs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }


    @PostMapping("/createNewClub")
    public ResponseEntity creatNewClub(@RequestBody ClubRequest clubRequest){
        Location location = locationService.createNewClub(clubRequest);

        return new ResponseEntity<>(location, HttpStatus.OK);

    }
    @DeleteMapping("/deleta-club/{id}")
    public ResponseEntity<Location> deleteClub(@PathVariable Long id) {
        locationService.deleteClub(id);
        return ResponseEntity.ok().body(null);
    }
    @PutMapping("/updateClub/{id}")
    public ResponseEntity<Location> updateClub(@PathVariable Long id, @RequestBody ClubRequest clubRequest) {
        Location location = locationService.updateClub(id, clubRequest);
        return ResponseEntity.ok().body(location);
    }
    @GetMapping("/get-club-by-id/{id}")
    public ResponseEntity<Location> getClubById(@PathVariable Long id) {
        return locationService.getClubById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/get-club-by-name/{name}")
    public ResponseEntity<Location> getClubByName(@PathVariable String name) {
        Location location = locationService.findByName(name);
        return ResponseEntity.ok().body(location);
    }
    @GetMapping("/get-club-by-Address/{adress}")
    public ResponseEntity<Location> getClubByAddress(@PathVariable String adress) {
        Location location = locationService.findByAdress(adress);
        return ResponseEntity.ok().body(location);
    }

}
