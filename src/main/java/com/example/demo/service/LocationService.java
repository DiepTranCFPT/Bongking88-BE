package com.example.demo.service;



import com.example.demo.eNum.ClubStatus;
import com.example.demo.entity.Location;
import com.example.demo.model.Request.ClubRequest;
import com.example.demo.respository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LocationService {
    @Autowired
    ClubRepository clubRepository;

    public Location createNewClub(ClubRequest clubRequest) {

        Location location = new Location();
        location.setName(clubRequest.getName());
        location.setDescription(clubRequest.getDescription());
        location.setAddress(clubRequest.getAddress());
        location.setHotline(clubRequest.getHotline());
        location.setOpenTime(clubRequest.getOpeningTime());
        location.setCloseTime(clubRequest.getClosingTime());
        location.setPhoto(clubRequest.getPhoto());
        location.setPrice(clubRequest.getPrice());

        return clubRepository.save(location);
    }
    // GET - Get All Club
    public List<Location> getAllClubs() {
        return clubRepository.findAll();
    }

    // GET - GetByClubId
    public Optional<Location> getClubById(Long id) {
        return clubRepository.findById(id);
    }

    public void deleteClub(Long id) {
        Optional<Location> optionalClub = clubRepository.findById(id);
        if (optionalClub.isPresent()) {
            clubRepository.delete(optionalClub.get());
        }else {
            System.out.println("Club is not existed to delete !!");
        }
    }
    public Location updateClub(Long id, ClubRequest clubRequest) {
        Optional<Location> optionalClub = clubRepository.findById(id);
        Location location = optionalClub.get();
        location.setName(clubRequest.getName());
        location.setDescription(clubRequest.getDescription());
        location.setAddress(clubRequest.getAddress());
        location.setHotline(clubRequest.getHotline());
        location.setOpenTime(clubRequest.getOpeningTime());
        location.setCloseTime(clubRequest.getClosingTime());
        location.setStatus(clubRequest.getStatus());
        location.setPrice(clubRequest.getPrice());


        return clubRepository.save(location);

    }

    public Location findByName(String name) {
        long id = -1;
        if (clubRepository.findByName(name) == null){
            return null;
        }
        else {
            id = clubRepository.findByName(name).getLocationId();
        }
        return clubRepository.findById(id).get();
    }
    public Location findByAdress(String address) {
        for (Location location : clubRepository.findAll()) {
            if (address.equals(location.getAddress())) {
                return location; // Return the location that matches the address
            }
        }
        return null; // Return null if no matching address is found
    }
    /**
     * @param id long {@link long id}
     * @param status bool {@link boolean status} true = active, false = inactive
     * @return {@link Location} or null
     */
    public Location setStatus(long id, boolean status) {
        String statuss = status ? "ACTIVE" : "INACTIVE";
        Optional<Location> location = clubRepository.findById(id);
        if (location.isPresent() ) {
            Location location1 = location.get();
            location1.setStatus(ClubStatus.valueOf(statuss));
            return clubRepository.save(location1);
        }
        return null;
    }
}