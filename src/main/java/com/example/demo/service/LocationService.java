package com.example.demo.service;



import com.example.demo.eNum.ClubStatus;
import com.example.demo.eNum.CourtStatus;
import com.example.demo.eNum.Role;
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
        if(account.getLocation() != null){ throw new GlobalException("bạn chỉ được tạo 1 location duy nhất");}
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

        if(open >= close) throw  new GlobalException("giờ mở cửa không được lớn hơn giờ đóng cửa ");

        for (double i = open; i < close; i += clubRequest.getTimeSlot()) {
            Slot slot = new Slot();
            double endTime = i + clubRequest.getTimeSlot();
            if(i + clubRequest.getTimeSlot() >= close ) break;
            slot.setTime(String.valueOf(i) + "h - " + String.valueOf(endTime)+ "h");
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
        List<Slot> slots =  new ArrayList<>();
        for(Slot slot : location.getSlots()){
            String timeRange = slot.getTime();
            String[] times = timeRange.split(" - ");
            int startTime = Integer.parseInt(times[0].replaceAll("[^0-9]", ""));
            int endTime = Integer.parseInt(times[1].replaceAll("[^0-9]", ""));
            if(location.getOpenTime() <= startTime && location.getCloseTime() >= endTime){
                slots.add(slot);
            }
        }
        location.setSlots(slots);
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
        location.setPhoto(clubRequest.getPhoto());
        location.setOpenTime(clubRequest.getOpeningTime());
        location.setCloseTime(clubRequest.getClosingTime());
        return clubRepository.save(location);
    }

    public List<Location> findByName(String name,String address) {
        List<Location> locations = clubRepository.findByNameContainingOrAddressContaining(name,address);
        locations = locations.stream().filter(location -> location.getStatus().equals(ClubStatus.ACTIVE)).toList();
        for(Location location : locations){
            location.setCourts(location.getCourts().stream().filter(c -> c.getStatus().equals(CourtStatus.ACTIVE)).toList());
        }
        return locations;
    }

    public Location getClubByOwnerId(Long id) {
        Account account = authenticationRepository.findById(id).orElseThrow(()-> new GlobalException("owner not found"));
        Location locations = clubRepository.findByOwner(account);
        return locations;
    }

    public Location updateStatusLocation(Long id, ClubStatus status) {
        Location location = clubRepository.findById(id).orElseThrow(()-> new GlobalException("location not found"));
        location.setStatus(status);
        return clubRepository.save(location);
    }

    public Location updateClubByOnwer(Long id, ClubRequest clubRequest) {
        Account account = authenticationRepository.findByLocationId(id);
        Location locations = clubRepository.findByOwner(account);
        if(locations != null){
            locations.setName(clubRequest.getName());
            locations.setDescription(clubRequest.getDescription());
            locations.setAddress(clubRequest.getAddress());
            locations.setHotline(clubRequest.getHotline());
            locations.setPhoto(clubRequest.getPhoto());
            locations.setOpenTime(clubRequest.getOpeningTime());
            locations.setCloseTime(clubRequest.getClosingTime());
        }
        else {
            throw new GlobalException("Owner not found location");
        }

        return clubRepository.save(locations);
    }


}