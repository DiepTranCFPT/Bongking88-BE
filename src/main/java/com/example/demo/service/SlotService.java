package com.example.demo.service;

import com.example.demo.eNum.SlotStatus;
import com.example.demo.entity.Location;
import com.example.demo.entity.Slot;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.SlotRequest;
import com.example.demo.respository.SlotRepository;
import com.example.demo.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {
        @Autowired
        SlotRepository slotRepository;

        @Autowired
        AccountUtils accountUtils;


//    public List<Slot> getSlotActiveByLocation(long id) {
//        return slotRepository.findByLocationIdAndStatus(id, SlotStatus.ACTIVE);
//    }

    public List<Slot> getSlotByLocation(long id) {
        return slotRepository.findByLocationId(id);

    }

        public Slot addnewSlot(SlotRequest slotRequest) {
            Location location = accountUtils.getCurrentUser().getLocation();
            Slot slot = new Slot();
            slot.setTime(slotRequest.getTime());
            slot.setPrice(slot.getPrice());
            slot.setLocation(location);
            return slotRepository.save(slot);
        }
        public Slot updateSlot(SlotRequest slotRequest, Long id) {
            Slot slot  = slotRepository.findById(id).orElseThrow(() -> new GlobalException("Slot not found"));
            slot.setTime(slotRequest.getTime());
            slot.setPrice(slotRequest.getPrice());
            return slotRepository.save(slot);
        }
        public Slot deleteSlot(Long id) {
            Slot slot  = slotRepository.findById(id).orElseThrow(() -> new GlobalException("Slot not found"));
            slot.setStatus(SlotStatus.INACTIVE);
             return   slotRepository.save(slot);
        }

//        public List<Slot> getAllSlot() {
//            return slotRepository.findAll();
//        }
//
//        public Slot getSlotById(Long id) {
//            return slotRepository.findById(id).orElse(null);
//        }
//
//        public List<Slot> getSlotByTime(String time) {
//            return slotRepository.findByTime(time);
//        }
//
//        public List<Slot> getSlotByPrice(Double price) {
//            return slotRepository.findByPrice(price);
//        }
//
////        public boolean CheckSlot(Long id){
////            Slot slot = slotRepository.findById(id).orElse(null);
////            return slot.isStatus();
////        }
//
//        public Slot UpdateStatus(Long id){
//            Slot slot = slotRepository.findById(id).orElse(null);
////            slot.setStatus(!slot.isStatus());
//            return slotRepository.save(slot);
//        }

}
