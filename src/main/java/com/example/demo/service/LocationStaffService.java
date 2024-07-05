package com.example.demo.service;

import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import com.example.demo.model.Request.LocationStaffRequest;
import com.example.demo.model.Request.RegisterRequest;
import com.example.demo.respository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    public Account addStaff(RegisterRequest locationStaffRequest) {
        Account locationStaff = new Account();
        locationStaff.setName(locationStaffRequest.getName());
        locationStaff.setPhone(locationStaffRequest.getPhone());
        locationStaff.setEmail(locationStaffRequest.getEmail());
        locationStaff.setPassword(passwordEncoder.encode(locationStaffRequest.getPassword()));
        locationStaff.setRole(Role.CLUB_STAFF); // Directly set the role

        return authenticationRepository.save(locationStaff);
    }

    public Account updateStaff(LocationStaffRequest locationStaffRequest, long id) {
        Optional<Account> staff = authenticationRepository.findById(id);
        if (staff.isPresent()) {
            Account locationStaff = staff.get();
            locationStaff.setName(locationStaffRequest.getStaffName());
            locationStaff.setPhone(locationStaffRequest.getPhone());
            locationStaff.setEmail(locationStaffRequest.getEmail());
            locationStaff.setPassword(passwordEncoder.encode(locationStaffRequest.getPassword()));
            locationStaff.setRole(Role.CLUB_STAFF); // Directly set the role

            return authenticationRepository.save(locationStaff);
        } else {
            throw new IllegalArgumentException("Staff not found with id: " + id);
        }
    }
    public List<Account> getAllStaff(Role role) {
        return authenticationRepository.findByRole(role);
    }
}
