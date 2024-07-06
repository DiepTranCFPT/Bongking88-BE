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
    public ResponseEntity<Account> addStaff(@RequestBody RegisterRequest locationStaffRequest) {
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


    @PostMapping("/add-new-slot")
    public ResponseEntity<Slot> addNewSlot(@RequestBody SlotRequest slotRequest) {
        Slot newSlot = slotService.addnewSlot(slotRequest);
        return ResponseEntity.ok(newSlot);
    }
    @PutMapping("/update-slot/{id}")
    public ResponseEntity<Slot> updateSlot(@RequestBody SlotRequest slotRequest, @PathVariable Long id) {
        Slot updatedSlot = slotService.updateSlot(slotRequest, id);
        return ResponseEntity.ok(updatedSlot);
    }
    @DeleteMapping("/delete-slot/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
        return ResponseEntity.ok("Slot deleted successfully");
    }

    @PutMapping("/update-owner/{id}")
    public ResponseEntity<Account> updateOwner(@RequestBody LocationOwnerRequest locationOwnerRequest, @PathVariable Long id) {
        Account updatedOwner = ownerService.updateOwner(locationOwnerRequest, id);
        if (updatedOwner != null) {
            return ResponseEntity.ok(updatedOwner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
