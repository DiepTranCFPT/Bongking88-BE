package com.example.demo.service;

import com.example.demo.eNum.AccoutStatus;
import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import com.example.demo.entity.Location;
import com.example.demo.model.Request.RegisterManagerRequest;
import com.example.demo.respository.AuthenticationRepository;
import com.example.demo.respository.LocationRepository;
import com.example.demo.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationStaffService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    AccountUtils accountUtils;





    public Account addStaff(RegisterManagerRequest locationStaffRequest) {

        Account accountOwner = accountUtils.getCurrentUser();

        Location location = locationRepository.findByOwnerId(accountOwner.getId());


        Account locationStaff = new Account();
        locationStaff.setName(locationStaffRequest.getName());
        locationStaff.setPhone(locationStaffRequest.getPhone());
        locationStaff.setEmail(locationStaffRequest.getEmail());
        locationStaff.setPassword(passwordEncoder.encode(locationStaffRequest.getPassword()));
        locationStaff.setRole(Role.CLUB_STAFF);
        locationStaff.setStatus(AccoutStatus.ACTIVE);
        locationStaff.setLocationStaff(location);
        return authenticationRepository.save(locationStaff);
    }

    public List<Account> getAllStaff(Role role) {
        return authenticationRepository.findByRole(role);
    }


    public List<Account> getStaffByOwneId(long id){
        Location location = locationRepository.findByOwnerId(id);
        List<Account> staffs = authenticationRepository.findAllByRoleAndAndLocationStaff(Role.CLUB_STAFF,location);
        return staffs;
    }
}
