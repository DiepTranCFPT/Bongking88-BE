package com.example.demo.controller;


import com.example.demo.entity.Transaction;
import com.example.demo.entity.Wallet;
import com.example.demo.model.Request.WalletReques;
import com.example.demo.respository.TransactionRepository;
import com.example.demo.service.TransactionService;
import com.example.demo.service.WalletService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name="api")
@RequestMapping("api/wallet")
@CrossOrigin("*")
public class WalletApi {


    @Autowired
    WalletService walletService;
    @Autowired
    TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<?> createWallet(@RequestBody WalletReques walletReques){
            walletService.createWallet(walletReques);
            return ResponseEntity.ok("success");
    }

    @GetMapping("/Transaction/{id}")
    public ResponseEntity<List<Transaction>> getTransactionByWalletId(@PathVariable long id){
        return ResponseEntity.ok(transactionService.getTransactionsByWalletId(id));
    }

    @GetMapping("/amount/{id}")
    public ResponseEntity getAmountWalletById(@PathVariable long id){
       Wallet wallet = walletService.getWalletById(id);
        return ResponseEntity.ok(wallet.getAmount());
    }
}
