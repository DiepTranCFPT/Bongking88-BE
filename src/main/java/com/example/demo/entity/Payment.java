package com.example.demo.entity;

import com.example.demo.eNum.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "transactionId")
    private String transactionId;

    @Column(name = "status")
    private BookingStatus status;

    @OneToOne(mappedBy = "payment")
    private Booking booking;

    @Column(name = "amount")
    private String amount;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
}
