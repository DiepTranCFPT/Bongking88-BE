package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private double amount;

    @OneToOne(mappedBy = "wallet")
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "wallet",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions;

}
