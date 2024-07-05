package com.example.demo.service;

import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import com.example.demo.model.EmailDetail;
import com.example.demo.model.Request.RegisterRequest;
import com.example.demo.respository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AdminService {
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
     @Autowired
    private EmailService emailService;

    public Account createAdmin(RegisterRequest registerRequest) {
        Account account = new Account();
        account.setName(registerRequest.getName());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setPhone(registerRequest.getPhone());
        account.setEmail(registerRequest.getEmail());

        account.setRole(Role.ADMIN);// Set the role directly

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(registerRequest.getEmail());
        emailDetail.setSubject("Thank you for registering.");
        emailDetail.setName(registerRequest.getName());
        emailDetail.setLink("http://booking88.online");
        emailService.sendMailTemplate(emailDetail);

        return authenticationRepository.save(account);
    }
}
