package com.example.demo.controller;

import com.example.demo.eNum.ClubStatus;
import com.example.demo.entity.Location;
import com.example.demo.model.Request.ClubRequest;
import com.example.demo.service.LocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@SecurityRequirement(name="api")
@CrossOrigin("*")
@RequestMapping("api/location")
public class LocationAPI {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity getAllClubs(@RequestParam(name = "name", required = false,defaultValue = "") String name,@RequestParam(name = "address", required = false,defaultValue = "") String address){
         List<Location> locations = locationService.findByName(name,address);
         return ResponseEntity.ok(locations);
    }

    @PostMapping
    public ResponseEntity<?> creatNewClub(@RequestBody ClubRequest clubRequest){
        Location location = locationService.createNewClub(clubRequest);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Location> deleteClub(@PathVariable Long id) {
        return ResponseEntity.ok( locationService.deleteClub(id));
    }
    @PutMapping("{id}")
    public ResponseEntity<Location> updateClub(@PathVariable Long id, @RequestBody ClubRequest clubRequest) {
        Location location = locationService.updateClub(id, clubRequest);
        return ResponseEntity.ok().body(location);
    }
    @GetMapping("{id}")
    public ResponseEntity<Location> getClubById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getClubById(id));
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity <Location> getClubByOwnerId(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getClubByOwnerId(id));
    }
    @PutMapping("/owner/{id}")
    public ResponseEntity<Location> updateClubByOnwer(@PathVariable Long id, @RequestBody ClubStatus clubRequest) {
        Location location = locationService.updateStatusLocation(id, clubRequest);
        return ResponseEntity.ok().body(location);
    }

}
