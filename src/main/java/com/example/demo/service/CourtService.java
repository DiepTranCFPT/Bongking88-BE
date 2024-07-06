package com.example.demo.service;


import com.example.demo.eNum.CourtStatus;
import com.example.demo.entity.Account;
import com.example.demo.entity.Court;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.CourtResquest;
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


    public List<Court> getCourtActiveByLocation(long id) {
        return courtRepository.findByLocationIdAndStatus(id,CourtStatus.ACCTIVE);
    }

    public List<Court> getCourtByLocation(long id) {
        return courtRepository.findByLocationId(id);

    }

    public Court getCourt(long id) {
       return  courtRepository.findById(id).orElseThrow(() -> new GlobalException("Court not found"));
    }

    public Court createCourt(CourtResquest courtResquest) {
        Account account = accountUtils.getCurrentUser();
        Court court = new Court();
        court.setName(courtResquest.getName());
        court.setStatus(CourtStatus.ACCTIVE);
        court.setLocation(account.getLocation());
        return courtRepository.save(court);

    }

    public Court deleteCourt(long id) {
       Court court = courtRepository.findById(id).orElseThrow(() -> new GlobalException("Court not found"));
        court.setStatus(CourtStatus.ISACCTIVE);
        return courtRepository.save(court);
    }


    public Court updateCourt(long id,CourtResquest courtResquest) {
        Court court = courtRepository.findById(id).orElseThrow(() -> new GlobalException("Court not found"));
        court.setName(courtResquest.getName());
        return courtRepository.save(court);
    }
}
