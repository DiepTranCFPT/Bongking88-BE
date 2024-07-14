package com.example.demo.service;


import com.example.demo.eNum.CourtStatus;
import com.example.demo.eNum.PromotionStatus;
import com.example.demo.eNum.SlotStatus;
import com.example.demo.entity.Account;
import com.example.demo.entity.Court;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.CourtResquest;
import com.example.demo.model.Response.CourtResponse;
import com.example.demo.respository.CourtRepository;
import com.example.demo.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CourtService {

    @Autowired
    CourtRepository courtRepository;

    @Autowired
    AccountUtils accountUtils;


//    public List<Court> getCourtActiveByLocation(long id) {
//        return courtRepository.findByLocationIdAndStatus(id,CourtStatus.ACTIVE);
//    }

    public List<Court> getCourtByLocation(long id) {
        return courtRepository.findByLocationId(id);
    }

    public CourtResponse getCourt(long id) {
        Court court = courtRepository.findById(id).orElseThrow(() -> new GlobalException("Court not found"));
        court.getLocation().setPromotions(court.getLocation().getPromotions().stream().filter(p -> p.getStatus().equals(PromotionStatus.ACTIVE)).toList());
        CourtResponse courtResponse = new CourtResponse();
        courtResponse.setId(court.getId());
        courtResponse.setName(court.getName());
        courtResponse.setLocation(court.getLocation());
        courtResponse.setStatus(court.getStatus());
        courtResponse.setImage(court.getImage());
        return  courtResponse;
    }

    public Court createCourt(CourtResquest courtResquest) {
        Account account = accountUtils.getCurrentUser();
        Court court = new Court();
        court.setName(courtResquest.getName());
        court.setImage(courtResquest.getImage());
        court.setStatus(CourtStatus.ACTIVE);
        court.setLocation(account.getLocation());

        account.getLocation().getCourts().add(court);


        return courtRepository.save(court);

    }

    public Court deleteCourt(long id) {
       Court court = courtRepository.findById(id).orElseThrow(() -> new GlobalException("Court not found"));
        court.setStatus(CourtStatus.INACTIVE);
        return courtRepository.save(court);
    }


    public Court updateCourt(long id,CourtResquest courtResquest) {
        Court court = courtRepository.findById(id).orElseThrow(() -> new GlobalException("Court not found"));
        court.setName(courtResquest.getName());
        court.setImage(courtResquest.getImage());
        return courtRepository.save(court);
    }






}
