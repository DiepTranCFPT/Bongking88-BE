package com.example.demo.controller;

import com.example.demo.eNum.BookingStatus;
import com.example.demo.entity.Booking;
import com.example.demo.service.BookingService;
import com.example.demo.service.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BookingAPI {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VNPAYService vnpayService;

    @PostMapping("/create")
    public Booking createBooking(@RequestBody Booking booking) throws Exception {
        return bookingService.createBooking(booking);
    }

    @GetMapping("/customer/{customerId}")
    public List<Booking> getBookingsByCustomerId(@PathVariable Long customerId) {
        return bookingService.getBookingsByCustomerId(customerId);
    }

    @PutMapping("/{bookingId}/status")
    public Booking updateBookingStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status) {
        return bookingService.updateBookingStatus(bookingId, status);
    }

//    @GetMapping("/vnpay_return")
//    public String handleVnpayReturn(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeyException {
//        int result = vnpayService.orderReturn(request);
//        if (result == 1) {
//            // Update payment status and booking status here
//            return "Payment successful";
//        } else if (result == 0) {
//            return "Payment failed";
//        } else {
//            return "Invalid signature";
//        }
//    }
}
