package com.example.demo.controller;

import com.example.demo.eNum.BookingStatus;
import com.example.demo.entity.Booking;
import com.example.demo.model.Request.BookingRequest;
import com.example.demo.service.BookingService;
import com.example.demo.service.VNPAYService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

@RestController
@SecurityRequirement(name="api")
@RequestMapping("/api/booking")
@CrossOrigin("*")
public class BookingAPI {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> booking(@RequestBody BookingRequest bookingRequest) throws ParseException {
        Booking booking =  bookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/staff")
    public ResponseEntity<Booking> bookingByStaff(@RequestBody BookingRequest bookingRequest) throws ParseException {
        Booking booking =  bookingService.createBookingByStaff(bookingRequest);
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

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<Booking>> getBookingsByLocation(@PathVariable long  id){
        List<Booking> bookings =  bookingService.getBookingsByOwner(id);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/cancelbookings/{id}")
    public ResponseEntity<Booking> cancelBookings(@PathVariable(name = "id" ) long  id){
        return ResponseEntity.ok(bookingService.CancelKookingFIXED(id));
    }

    @PutMapping("/cancelbookings/{idbk}/{idslot}")
    public ResponseEntity<Booking> cancelBookings(@PathVariable(name = "idbk" ) long  idbk, @PathVariable(name = "idslot" ) long  idslot){
        return ResponseEntity.ok(bookingService.CancelKookingFIXEDinSLOT(idbk, idslot));
    }

    @GetMapping("/getAllBooking")
    public ResponseEntity<List<Booking>> getAllBooking(){
        return ResponseEntity.ok(bookingService.getAllBookingSuccess());
    }

    @GetMapping("/ownerList")
    public ResponseEntity getBookingsByLocationList(){
         int numeber =  bookingService.getSizeBooking();
        return ResponseEntity.ok(numeber);
    }

    @PutMapping("/Checking/{id}")
    public ResponseEntity<Boolean> Checking(@PathVariable(name = "id" ) long  id, @RequestParam String code){
        return ResponseEntity.ok(bookingService.checkCode(id, code));
    }


    @GetMapping("/staff/{id}")
    public ResponseEntity<List<Booking>> getBookingByStaff(@PathVariable long id){
        return ResponseEntity.ok(bookingService.getBookingByStaff(id));
    }

}
