package com.example.demo.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.example.demo.entity.Account;
import com.example.demo.entity.Wallet;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.WalletReques;
import com.example.demo.respository.AuthenticationRepository;
import com.example.demo.respository.WalletRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import javassist.bytecode.StackMapTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {


    private AuthenticationRepository authenticationRepository;

    private WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository,AuthenticationRepository authenticationRepository){
        this.walletRepository = walletRepository;
        this.authenticationRepository = authenticationRepository;
    }


    public void createWallet(WalletReques walletReques){
        Optional<Account> account = authenticationRepository.findById(walletReques.getIdAccount());
        Wallet wallet = walletRepository.findByAccount_Id(account.get().getId());

            wallet.setAmount(wallet.getAmount() + walletReques.getAmount());
            walletRepository.save(wallet);
        return;
    }



}
