package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.model.Request.*;
import com.example.demo.service.AdminService;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name="api")
@CrossOrigin("*")
//@CrossOrigin(origins = "http://localhost:5173/")

public class AdminAPI {

    @Autowired
    AuthenticationService authenticationService;///git branch

    @Autowired
    EmailService emailService;

    @Autowired
    AdminService adminService;


    @GetMapping("/getAll")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = authenticationService.all();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/create-admin")
    public ResponseEntity RegisterAdmin(@RequestBody RegisterRequest responseRequest) {
        Account account = adminService.createAdmin(responseRequest);
        return ResponseEntity.ok(account);
    }


    @DeleteMapping("/delete-account/{id}")
    public ResponseEntity<?> deleteAccountByid(@PathVariable Long id) {
        authenticationService.deleteAccount(id);
        return ResponseEntity.ok("Delete success");
    }

    @PutMapping("/update-account/{id}")
    public ResponseEntity updateAccount(@RequestBody UpdateRequest responseRequest,@PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.updateAccount(responseRequest,id));
    }
    @GetMapping("/get-account-by-id/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(authenticationService.findById(id));
    }
    @GetMapping("cccccc")
    public ResponseEntity ccc() {
        return ResponseEntity.ok("ccc");
    }



}
