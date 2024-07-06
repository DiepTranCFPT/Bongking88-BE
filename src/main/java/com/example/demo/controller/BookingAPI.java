package com.example.demo.controller;

import com.example.demo.eNum.BookingStatus;
import com.example.demo.entity.Booking;
import com.example.demo.service.BookingService;
import com.example.demo.service.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin("*")
public class BookingAPI {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VNPAYService vnpayService;

//    @PostMapping
//    public Booking createBooking(@RequestBody Booking booking) throws Exception {
//        return bookingService.createBooking(booking);
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity getBookingsByCustomerId(@PathVariable Long id) {
//        return ResponseEntity.ok(bookingService.getBookingsByCustomerId(id));
//    }
//
//    @PutMapping("/{bookingId}/status")
//    public Booking updateBookingStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status) {
//        return bookingService.updateBookingStatus(bookingId, status);
//    }

}
