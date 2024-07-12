package com.example.demo.controller;


import com.example.demo.entity.Wallet;
import com.example.demo.model.Request.WalletReques;
import com.example.demo.service.WalletService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name="api")
@RequestMapping("api/wallet")
@CrossOrigin("*")
public class WalletApi {


    @Autowired
    WalletService walletService;

    @PostMapping("")
    public ResponseEntity<?> createWallet(@RequestBody WalletReques walletReques){
            walletService.createWallet(walletReques);
            return ResponseEntity.ok("success");
    }
}
