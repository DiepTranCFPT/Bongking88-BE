package com.example.demo.entity;

import com.example.demo.eNum.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long BookingId;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Account customer;

    @Column(name = "BookingDate")
    private String BookingDate;

    @Column(name = "totalPrice")
    private String totalPrice;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "BookingType")
    private String BookingType;

    @ManyToOne
    @JoinColumn(name = "PaymentId")
    private Payment payment;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingDetail> bookingDetails;

    @ManyToOne
    @JoinColumn(name = "Promotion")
    private Promotion promotion;
}
