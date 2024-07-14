package com.example.demo.model.Request;



import com.example.demo.eNum.BookingTypeEnum;
import com.example.demo.entity.Account;
import com.example.demo.entity.BookingDetail;
import com.example.demo.entity.Promotion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequest {

    @Enumerated(EnumType.STRING)
    private BookingTypeEnum BookingType;
    long idPromotion;
    long idUser;
    long idLocation;
    String date;
    List<BookingDetailRequest> bookingDetailRequests;
}
