package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.model.Request.*;
import com.example.demo.service.AdminService;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.EmailService;
import com.example.demo.service.OwnerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name="api")
@RequestMapping("api/admin")
@CrossOrigin("*")
public class AdminAPI {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    EmailService emailService;

    @Autowired
    AdminService adminService;

    @Autowired
    OwnerService ownerService;


    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = authenticationService.all();
        return ResponseEntity.ok(accounts);
    }
    @PostMapping("/account")
    public ResponseEntity addOwner(@RequestBody LocationOwnerRequest locationOwnerRequest) {
            Account newOwner = ownerService.addOwner(locationOwnerRequest);
            return ResponseEntity.ok(newOwner);
    }
    @DeleteMapping("/account/{id}")
    public ResponseEntity deleteAccountByid(@PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.deleteAccount(id));
    }

    @PutMapping("/account/{id}")
    public ResponseEntity updateOwner(@RequestBody UpdateRequest responseRequest, @PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.updateAccount(responseRequest,id));
    }
    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.findById(id));
    }







}
