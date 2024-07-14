package com.example.demo.service;

import com.example.demo.eNum.AccoutStatus;
import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import com.example.demo.entity.Location;
import com.example.demo.entity.Wallet;
import com.example.demo.exception.AuthException;
import com.example.demo.model.EmailDetail;
import com.example.demo.model.Request.LocationOwnerRequest;
import com.example.demo.respository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private EmailService emailService;

    public Account addOwner(LocationOwnerRequest locationOwnerRequest) {
        Account locationOwner = new Account();
        locationOwner.setName(locationOwnerRequest.getName());
        locationOwner.setPhone(locationOwnerRequest.getPhone());
        locationOwner.setEmail(locationOwnerRequest.getEmail());
        locationOwner.setPassword(passwordEncoder.encode(locationOwnerRequest.getPassword()));
        locationOwner.setRole(Role.CLUB_OWNER);
        locationOwner.setStatus(AccoutStatus.ACTIVE);

        Wallet wallet = new Wallet();
        wallet.setAmount(0);
        wallet.setAccount(locationOwner);
        locationOwner.setWallet(wallet);


        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(locationOwner.getEmail());
        emailDetail.setSubject("Thank you for registering.");
        emailDetail.setName(locationOwner.getName());
        emailDetail.setLink("http://booking88.online");
        emailService.sendMailTemplateOwner(emailDetail);

        return authenticationRepository.save(locationOwner);
    }

    public Account updateOwner(LocationOwnerRequest locationOwnerRequest, Long id) {
        Optional<Account> ownerOptional = authenticationRepository.findById(id);
        if (ownerOptional.isPresent()) {
            Account locationOwner = ownerOptional.get();
            locationOwner.setName(locationOwnerRequest.getName());
            locationOwner.setPhone(locationOwnerRequest.getPhone());
            locationOwner.setEmail(locationOwnerRequest.getEmail());
            locationOwner.setPassword(passwordEncoder.encode(locationOwnerRequest.getPassword()));
            locationOwner.setRole(Role.CLUB_OWNER); // Directly set the role

            return authenticationRepository.save(locationOwner);
        } else {
            throw new IllegalArgumentException("Owner not found with id: " + id);
        }
    }
}

///test