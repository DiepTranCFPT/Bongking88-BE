package com.example.demo.service;


import com.example.demo.eNum.TransactionType;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.Wallet;
import com.example.demo.respository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;


    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public List<Transaction> AddTransaction(List<Transaction> transactions, Wallet wallet,
                                            double amount,
                                            TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transactions.add(transaction);
        transaction.setWallet(wallet);
        return transactions;
    }

    public  List<Transaction> getTransactionsByWalletId(long id){
        return transactionRepository.findByWallet_Id(id);
    }


}
