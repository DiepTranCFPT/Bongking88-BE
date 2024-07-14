package com.example.demo.controller;


import com.example.demo.eNum.Role;
import com.example.demo.entity.*;
import com.example.demo.model.Request.*;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.LocationStaffService;
import com.example.demo.service.OwnerService;
import com.example.demo.service.SlotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "api")
@RequestMapping("api/owner")
@CrossOrigin("*")
public class OwnerAPI {
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private SlotService slotService;

    @Autowired
    private LocationStaffService locationStaffService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/account")
    public ResponseEntity<Account> addStaff(@RequestBody RegisterManagerRequest locationStaffRequest) {
        Account newStaff = locationStaffService.addStaff(locationStaffRequest);
        return ResponseEntity.ok(newStaff);
    }

     @DeleteMapping("{id}")
     public ResponseEntity deleteStaff(@PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.deleteAccount(id));
     }

    @PutMapping("/account/{id}")
    public ResponseEntity<Account> updateStaff(@RequestBody UpdateRequest updateRequest, @PathVariable Long id) {
            Account updatedStaff = authenticationService.updateAccount(updateRequest, id);
            return ResponseEntity.ok(updatedStaff);
    }
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllStaff() {
        List<Account> staffs = locationStaffService.getAllStaff(Role.CLUB_STAFF);
        return ResponseEntity.ok(staffs);
    }

    @GetMapping("/accountss/{id}")
    public ResponseEntity<List<Account>> getStaffByOwneId(@PathVariable(value = "id") Long id) {
        List<Account> staffs = locationStaffService.getStaffByOwneId(id);
        return ResponseEntity.ok(staffs);
    }
}
