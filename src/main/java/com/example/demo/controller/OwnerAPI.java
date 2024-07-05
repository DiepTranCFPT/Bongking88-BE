package com.example.demo.controller;


import com.example.demo.eNum.Role;
import com.example.demo.entity.*;
import com.example.demo.model.Request.*;
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
@CrossOrigin("*")
public class OwnerAPI {
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private SlotService slotService;

    @Autowired
    private LocationStaffService locationStaffService;

    @PostMapping("/add-staff")
    public ResponseEntity<Account> addStaff(@RequestBody RegisterRequest locationStaffRequest) {
        Account newStaff = locationStaffService.addStaff(locationStaffRequest);
        return ResponseEntity.ok(newStaff);
    }

    @PutMapping("/update-staff/{id}")
    public ResponseEntity<Account> updateStaff(@RequestBody LocationStaffRequest locationStaffRequest, @PathVariable Long id) {
        Account updatedStaff = locationStaffService.updateStaff(locationStaffRequest, id);
        if (updatedStaff != null) {
            return ResponseEntity.ok(updatedStaff);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllStaff")
    public ResponseEntity<List<Account>> getAllStaff() {
        List<Account> staffs = locationStaffService.getAllStaff(Role.CLUB_STAFF);
        return ResponseEntity.ok(staffs);
    }
    // Lấy danh sách tất cả các sân

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
    @PostMapping("/add-owner")
    public ResponseEntity<?> addOwner(@RequestBody LocationOwnerRequest locationOwnerRequest) {
        try {
            Account newOwner = ownerService.addOwner(locationOwnerRequest);
            return ResponseEntity.ok(newOwner);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
//    @DeleteMapping("delete-owner")
//    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
//        ownerService.deleteOwner(id);
//        return ResponseEntity.noContent().build();
//    }

}
