package com.example.demo.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.example.demo.eNum.TransactionType;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.Wallet;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.WalletReques;
import com.example.demo.respository.AuthenticationRepository;
import com.example.demo.respository.TransactionRepository;
import com.example.demo.respository.WalletRepository;
import com.example.demo.utils.AccountUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import javassist.bytecode.StackMapTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {


    private final AccountUtils accountUtils;
    private AuthenticationRepository authenticationRepository;

    private WalletRepository walletRepository;

    private TransactionRepository transactionRepository;


    @Autowired
    public WalletService(WalletRepository walletRepository, AuthenticationRepository authenticationRepository,
                         TransactionRepository transactionRepository, AccountUtils accountUtils){
        this.walletRepository = walletRepository;
        this.authenticationRepository = authenticationRepository;
        this.transactionRepository = transactionRepository;
        this.accountUtils = accountUtils;
    }


    public Wallet createWallet(WalletReques walletReques){
        Optional<Account> account = authenticationRepository.findById(walletReques.getIdAccount());
        Wallet wallet = walletRepository.findByAccount_Id(account.get().getId());

            wallet.setAmount(wallet.getAmount() + walletReques.getAmount());
            Transaction tx = new Transaction();
            tx.setAmount(walletReques.getAmount());
            tx.setTransactionType(TransactionType.RECHARGE);
            tx.setWallet(wallet);
            transactionRepository.save(tx);
            List<Transaction> transactions = wallet.getTransactions();
            transactions.add(tx);
            wallet.setTransactions(transactions);
        return walletRepository.save(wallet);
    }


    public Wallet createTransaction(Wallet wallet, Transaction transaction) {



        return null;
    }

    public Double getWalletById(long id) {
        Account account =accountUtils.getCurrentUser();
        double amount = account.getWallet().getAmount();
        return amount;
    }
    public Wallet getWalletAccountById(long id) {
        Account account =accountUtils.getCurrentUser();
        return account.getWallet();
    }

}
