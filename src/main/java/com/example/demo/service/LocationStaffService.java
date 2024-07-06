package com.example.demo.service;

import com.example.demo.eNum.AccoutStatus;
import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import com.example.demo.entity.Location;
import com.example.demo.exception.AuthException;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.LocationStaffRequest;
import com.example.demo.model.Request.RegisterManagerRequest;
import com.example.demo.model.Request.RegisterRequest;
import com.example.demo.respository.AuthenticationRepository;
import com.example.demo.respository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationStaffService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    LocationRepository locationRepository;


    public Account addStaff(RegisterManagerRequest locationStaffRequest) {
        Location location = locationRepository.findById(locationStaffRequest.getLocationId()).orElseThrow(()-> new GlobalException("staff needs to belong to location"));
        Account locationStaff = new Account();
        locationStaff.setName(locationStaffRequest.getName());
        locationStaff.setPhone(locationStaffRequest.getPhone());
        locationStaff.setEmail(locationStaffRequest.getEmail());
        locationStaff.setPassword(passwordEncoder.encode(locationStaffRequest.getPassword()));
        locationStaff.setRole(Role.CLUB_STAFF);
        locationStaff.setStatus(AccoutStatus.ACTIVE);
        locationStaff.setLocation(location);
        return authenticationRepository.save(locationStaff);
    }

    public List<Account> getAllStaff(Role role) {
        return authenticationRepository.findByRole(role);
    }
}
