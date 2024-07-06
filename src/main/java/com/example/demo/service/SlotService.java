package com.example.demo.service;

import com.example.demo.entity.Slot;
import com.example.demo.model.Request.SlotRequest;
import com.example.demo.respository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SlotService {
        @Autowired
        SlotRepository slotRepository;

        public Slot addnewSlot(SlotRequest slotRequest) {
            Slot slot = new Slot();
            slot.setTime(slotRequest.getTime());
            slot.setPrice(slot.getPrice());
            return slotRepository.save(slot);
        }
        public Slot updateSlot(SlotRequest slotRequest,Long id) {
            Optional<Slot> slot  = slotRepository.findById(id);
            slot.get().setTime(slotRequest.getTime());
            slot.get().setPrice(slotRequest.getPrice());
            return slotRepository.save(slot.get());
        }
        public void deleteSlot(Long id) {
            Optional<Slot> slot  = slotRepository.findById(id);
            if (slot.isPresent()) {
                slotRepository.delete(slot.get());
            }else {
                System.out.println("Slot not found");
            }
        }
        public List<Slot> getAllSlot() {
            return slotRepository.findAll();
        }

        public Slot getSlotById(Long id) {
            return slotRepository.findById(id).orElse(null);
        }

        public List<Slot> getSlotByTime(Integer time) {
            return slotRepository.findByTime(time);
        }

        public List<Slot> getSlotByPrice(Double price) {
            return slotRepository.findByPrice(price);
        }

        public boolean CheckSlot(Long id){
            Slot slot = slotRepository.findById(id).orElse(null);
            return slot.isStatus();
        }

        public Slot UpdateStatus(Long id){
            Slot slot = slotRepository.findById(id).orElse(null);
            slot.setStatus(!slot.isStatus());
            return slotRepository.save(slot);
        }

}
