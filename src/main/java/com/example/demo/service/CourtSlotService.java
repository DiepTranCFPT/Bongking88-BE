package com.example.demo.service;


import com.example.demo.eNum.CourtSlotStatus;
import com.example.demo.entity.CourtSlot;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Response.CourtSlotResponse;
import com.example.demo.respository.CourtSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourtSlotService {

    @Autowired
    CourtSlotRepository courtSlotRepository;


    public List<CourtSlotResponse>
    getCourtSlotByCustomerId(long id) {
        List<CourtSlot> courtSlots = courtSlotRepository.findByAccountIdAndStatus(id,CourtSlotStatus.PENDING);
        List<CourtSlotResponse> courtSlotResponses = new ArrayList<>();
        for(CourtSlot slot : courtSlots){
            CourtSlotResponse courtSlotResponse = new CourtSlotResponse();
            courtSlotResponse.setCourt(slot.getCourt());
            courtSlotResponse.setSlot(slot.getSlot());
            courtSlotResponse.setLocation(slot.getCourt().getLocation());
            courtSlotResponse.setStatus(slot.getStatus());
            courtSlotResponse.setId(slot.getId());
            courtSlotResponse.setDate(slot.getDate());
            courtSlotResponse.setAccount(slot.getAccount());
            courtSlotResponse.setBookingDetail(slot.getBookingDetail());
            courtSlotResponses.add(courtSlotResponse);
        }
        return courtSlotResponses;
    }

    public List<CourtSlot> getCourtSlotByLocation(long id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String currentDate = dateFormat.format(new Date());
        return courtSlotRepository.findByLocationAndStatusAndDate(id, currentDate);
    }

    public static String getCurrentHourAsString() {
        Date currentDate = new Date();
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH"); // 24-hour format
        return hourFormat.format(currentDate);
    }
    @Scheduled(fixedRate = 60000)
    public void checkCourtSlot() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String currentDate = dateFormat.format(new Date());
        List<CourtSlot> courtSlots = courtSlotRepository.findByStatusAndDate(CourtSlotStatus.ACTIVE, currentDate);
        for(CourtSlot slot : courtSlots){
            String timeRange = slot.getSlot().getTime();
            String[] times = timeRange.split(" - ");
            int endTime = Integer.parseInt(times[1].replaceAll("[^0-9]", ""));
            if(Integer.parseInt(getCurrentHourAsString()) >= endTime){
                slot.setStatus(CourtSlotStatus.INACTIVE);
                courtSlotRepository.save(slot);
            }
        };
    }

    public CourtSlot updateCourtSlot(long id) {
        CourtSlot courtSlot = courtSlotRepository.findById(id).orElseThrow(() -> new GlobalException("không tìm thấy courtSlot này"));
        courtSlot.setStatus(CourtSlotStatus.ACTIVE);
        return courtSlotRepository.save(courtSlot);
    }





}
