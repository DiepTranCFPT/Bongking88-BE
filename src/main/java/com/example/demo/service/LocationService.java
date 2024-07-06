package com.example.demo.service;



import com.example.demo.eNum.ClubStatus;
import com.example.demo.eNum.CourtStatus;
import com.example.demo.eNum.SlotStatus;
import com.example.demo.entity.Account;
import com.example.demo.entity.Court;
import com.example.demo.entity.Location;
import com.example.demo.entity.Slot;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.ClubRequest;
import com.example.demo.respository.AuthenticationRepository;
import com.example.demo.respository.LocationRepository;
import com.example.demo.respository.SlotRepository;
import com.example.demo.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    SlotRepository slotRepository;

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

        int open = location.getOpenTime();
        int close = location.getCloseTime();


        for (int i = 0; i < close - open; i++) {
            Slot slot = new Slot();
            slot.setTime(String.valueOf(open + i) + "h - " + String.valueOf(open + 1 + i)+ "h") ;
            slot.setPrice(clubRequest.getPriceSlot());
            slot.setLocation(location);
            slot.setStatus(SlotStatus.ACTIVE);
            location.getSlots().add(slot);
        }
        return clubRepository.save(location);
    }
    // GET - Get All Club
    public List<Location> getAllClubs() {
        return clubRepository.findAll();
    }

    // GET - GetByClubId
    public Location getClubById(Long id) {
        Location location = clubRepository.findById(id).orElseThrow(()-> new GlobalException("location not found"));
        location.setCourts(location.getCourts().stream().filter(c -> c.getStatus().equals(CourtStatus.ACTIVE)).toList());
        return location;
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
        List<Location> locations = clubRepository.findByNameContainingOrAddressContaining(name,address);
        List<Location> locations1 = new ArrayList<>();
        for(Location location : locations){
            location.setCourts(location.getCourts().stream().filter(c -> c.getStatus().equals(CourtStatus.ACTIVE)).toList());
            locations1.add(location);
        }


        return locations1;
    }


}