package com.example.demo.model.Request;

import com.example.demo.entity.Slot;
import lombok.Data;

import java.util.List;

@Data
public class BookingDetailRequest {
//    long idSlot;
    String date;
    long idSlot;
}
