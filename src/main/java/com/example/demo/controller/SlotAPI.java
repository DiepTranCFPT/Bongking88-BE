package com.example.demo.controller;


import com.example.demo.entity.Court;
import com.example.demo.entity.Slot;
import com.example.demo.model.Request.SlotRequest;
import com.example.demo.service.SlotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name="api")
@RequestMapping("api/slot")
@CrossOrigin("*")
public class SlotAPI {

    @Autowired
    SlotService slotService;


//    @GetMapping("/location/{id}")
//    public ResponseEntity getCourtActiveByLocation(@PathVariable long id){
//        List<Slot> slots = slotService.getSlotActiveByLocation(id);
//        return  ResponseEntity.ok(slots);
//    }

    @GetMapping("/location/{id}")
    public ResponseEntity getCourtByLocation(@PathVariable long id){
        List<Slot> slots = slotService.getSlotByLocation(id);
        return  ResponseEntity.ok(slots);
    }

    @PostMapping
    public ResponseEntity<Slot> addNewSlot(@RequestBody SlotRequest slotRequest) {
        Slot newSlot = slotService.addnewSlot(slotRequest);
        return ResponseEntity.ok(newSlot);
    }
    @PutMapping("{id}")
    public ResponseEntity<Slot> updateSlot(@RequestBody SlotRequest slotRequest, @PathVariable Long id) {
        Slot updatedSlot = slotService.updateSlot(slotRequest, id);
        return ResponseEntity.ok(updatedSlot);
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteSlot(@PathVariable Long id) {
        return ResponseEntity.ok(slotService.deleteSlot(id));
    }
}
