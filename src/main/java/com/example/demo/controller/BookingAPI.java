package com.example.demo.controller;

import com.example.demo.eNum.BookingStatus;
import com.example.demo.entity.Booking;
import com.example.demo.model.Request.BookingRequest;
import com.example.demo.service.BookingService;
import com.example.demo.service.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin("*")
public class BookingAPI {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VNPAYService vnpayService;

    @PostMapping
    public ResponseEntity<Booking> booking(@RequestBody BookingRequest bookingRequest) throws ParseException {
        Booking booking =  bookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(booking);
    }
    @PostMapping("/price")
    public ResponseEntity<Double> price(@RequestBody BookingRequest bookingRequest){
        double price =  bookingService.price(bookingRequest);
        return ResponseEntity.ok(price);
    }
    @GetMapping("{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable long  id){
        Booking booking =  bookingService.getBooking(id);
        return ResponseEntity.ok(booking);
    }
    @GetMapping("/account/{id}")
    public ResponseEntity<List<Booking>> getBookingsByCustomerId(@PathVariable long  id){
        List<Booking> bookings =  bookingService.getBookingsByCustomerId(id);
        return ResponseEntity.ok(bookings);
    }


    //getbookings by location id
    @GetMapping("/owner/{id}")
    public ResponseEntity<List<Booking>> getBookingsByLocation(@PathVariable long  id){
        List<Booking> bookings =  bookingService.getBookingsByOwner(id);
        return ResponseEntity.ok(bookings);
    }


}
