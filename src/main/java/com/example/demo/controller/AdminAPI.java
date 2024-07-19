package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.Location;
import com.example.demo.model.Request.*;
import com.example.demo.respository.LocationRepository;
import com.example.demo.service.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    LocationRepository locationService;

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = authenticationService.all();
        return ResponseEntity.ok(accounts);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/owner")
    public ResponseEntity<List<Account>> getAllOwner() {
        List<Account> accounts = authenticationService.allOwner();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/register")
    public ResponseEntity<Account> addOwner(@RequestBody RegisterRequest locationOwnerRequest) {
            Account newOwner = ownerService.addOwner(locationOwnerRequest);
            return ResponseEntity.ok(newOwner);
    }
    @DeleteMapping("/account/{id}")
    public ResponseEntity<Account> deleteAccountByid(@PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.deleteAccount(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/account/{id}")
    public ResponseEntity<Account> updateOwner(@RequestBody UpdateRequest responseRequest, @PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.updateAccount(responseRequest,id));
    }
    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.findById(id));
    }

    @GetMapping("/location")
    public ResponseEntity<List<Location>> getLocation() {
        return ResponseEntity.ok(locationService.findAll());
    }

}
