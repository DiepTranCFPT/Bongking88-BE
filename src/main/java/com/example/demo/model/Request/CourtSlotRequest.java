package com.example.demo.model.Request;

import com.example.demo.entity.BookingDetail;
import lombok.Data;

import java.util.List;

@Data
public class CourtSlotRequest {
    private BookingDetail bookingDetail;
    private List<SlotRequest> slots;
}