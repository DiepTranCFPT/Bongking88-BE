package com.example.demo.service;



import com.example.demo.eNum.ClubStatus;
import com.example.demo.entity.Account;
import com.example.demo.entity.Location;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.ClubRequest;
import com.example.demo.respository.AuthenticationRepository;
import com.example.demo.respository.LocationRepository;
import com.example.demo.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LocationService {
    @Autowired
    LocationRepository clubRepository;

    @Autowired
    AccountUtils accountUtils;

    @Autowired
    AuthenticationRepository authenticationRepository;

    public Location createNewClub(ClubRequest clubRequest) {
        Account account = authenticationRepository.findById(clubRequest.getOwnerId()).orElseThrow(()->new GlobalException("location needs an owner"));
        Location location = new Location();
        location.setName(clubRequest.getName());
        location.setDescription(clubRequest.getDescription());
        location.setAddress(clubRequest.getAddress());
        location.setHotline(clubRequest.getHotline());
        location.setOpenTime(clubRequest.getOpeningTime());
        location.setCloseTime(clubRequest.getClosingTime());
        location.setPhoto(clubRequest.getPhoto());
        location.setOwner(account);
        location.setStatus(ClubStatus.ACTIVE);
        return clubRepository.save(location);
    }
    // GET - Get All Club
    public List<Location> getAllClubs() {
        return clubRepository.findAll();
    }

    // GET - GetByClubId
    public Location getClubById(Long id) {
        return clubRepository.findById(id).orElseThrow(()-> new GlobalException("location not found"));
    }

    public Location deleteClub(Long id) {
        Location optionalClub = clubRepository.findById(id).orElseThrow(()-> new GlobalException("location not found"));
        optionalClub.setStatus(ClubStatus.INACTIVE);
        return   clubRepository.save(optionalClub);

    }
    public Location updateClub(Long id, ClubRequest clubRequest) {
        Location location = clubRepository.findById(id).orElseThrow(()-> new GlobalException("location not found"));
        location.setName(clubRequest.getName());
        location.setDescription(clubRequest.getDescription());
        location.setAddress(clubRequest.getAddress());
        location.setHotline(clubRequest.getHotline());
        location.setOpenTime(clubRequest.getOpeningTime());
        location.setCloseTime(clubRequest.getClosingTime());
        return clubRepository.save(location);
    }

    public List<Location> findByName(String name,String address) {
        return clubRepository.findByNameContainingOrAddressContaining(name,address);
    }


}