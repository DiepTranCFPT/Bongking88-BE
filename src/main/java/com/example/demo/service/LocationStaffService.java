package com.example.demo.service;

import com.example.demo.eNum.AccoutStatus;
import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import com.example.demo.entity.Location;
import com.example.demo.entity.Wallet;
import com.example.demo.exception.AuthException;
import com.example.demo.model.EmailDetail;
import com.example.demo.model.Request.RegisterManagerRequest;
import com.example.demo.respository.AuthenticationRepository;
import com.example.demo.respository.LocationRepository;
import com.example.demo.respository.WalletRepository;
import com.example.demo.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    EmailService emailService;

    public Account addStaff(RegisterManagerRequest registerRequest) {

        Account accountOwner = accountUtils.getCurrentUser();
        if(accountOwner.getRole() != Role.CLUB_OWNER){
            throw new RuntimeException("You are not owner");
        }else {
            Location location = locationRepository.findByOwnerId(accountOwner.getId());

            Account account = new Account();
            account.setName(registerRequest.getName());
            account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            account.setPhone(registerRequest.getPhone());
            account.setEmail(registerRequest.getEmail());
            account.setRole(Role.CLUB_STAFF);
            account.setStatus(AccoutStatus.INACTIVE);
            account.setEnable(false);
            account.setVerificationCode(UUID.randomUUID().toString());

            account.setLocationStaff(location);
            account.setLocation(location);

            try {
                account = authenticationRepository.save(account);
            } catch (DataIntegrityViolationException e) {
                throw new AuthException("Duplicate");
            }

            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(registerRequest.getEmail());
            emailDetail.setSubject("Verify your registration");
            emailDetail.setName(registerRequest.getName());
            String verifyURL = "http://157.230.43.225:8080/api/verify?code="+account.getVerificationCode();
            emailDetail.setLink(verifyURL);
            emailDetail.setButtonValue("Verify Email");
            emailService.sendMailTemplate(emailDetail);
            return authenticationRepository.save(account);

        }
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
