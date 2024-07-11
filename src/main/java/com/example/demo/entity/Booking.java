package com.example.demo.entity;

import com.example.demo.eNum.BookingStatus;
import com.example.demo.eNum.BookingTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String BookingDate;


    private String totalPrice;

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;


    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Enumerated(EnumType.STRING)
    private BookingTypeEnum BookingType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Account customer;


    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetails;

    @ManyToOne
    @JoinColumn(name = "Promotion")
    private Promotion promotion;
}
