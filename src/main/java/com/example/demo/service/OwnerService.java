package com.example.demo.service;

import com.example.demo.eNum.AccoutStatus;
import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import com.example.demo.entity.Wallet;
import com.example.demo.exception.AuthException;
import com.example.demo.model.EmailDetail;
import com.example.demo.model.Request.LocationOwnerRequest;
import com.example.demo.model.Request.RegisterRequest;
import com.example.demo.respository.AuthenticationRepository;
import com.example.demo.respository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class OwnerService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    WalletRepository walletRepository;


    @Transactional
    public Account addOwner(RegisterRequest registerRequest) {
        Account account = new Account();
        account.setName(registerRequest.getName());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setPhone(registerRequest.getPhone());
        account.setEmail(registerRequest.getEmail());
        account.setRole(Role.CUSTOMER);
        account.setStatus(AccoutStatus.INACTIVE);
        account.setEnable(false);
        account.setVerificationCode(UUID.randomUUID().toString());

        Wallet wallet = new Wallet();
        wallet.setAccount(account);
        wallet.setAmount(0.0);
        walletRepository.save(wallet);
        account.setWallet(wallet);

        try {
            account = authenticationRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            throw new AuthException("Duplicate");
        }

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(registerRequest.getEmail());
        emailDetail.setSubject("Verify your registration");
        emailDetail.setName(registerRequest.getName());
        String verifyURL = "http://localhost:8080/api/verify?code="+account.getVerificationCode();
        emailDetail.setLink(verifyURL);
        emailDetail.setButtonValue("Verify Email");
        emailService.sendMailTemplate(emailDetail);
        return account;

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