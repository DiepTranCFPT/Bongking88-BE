package com.example.demo.service;

import com.example.demo.eNum.BookingStatus;
import com.example.demo.entity.*;
import com.example.demo.respository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookingService {



    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private VNPAYService vnpayService;
    @Autowired
    private SlotService slotService;




//    public List<Booking> getAllBookings() {
//        return bookingRepository.findAll();
//    }

//    public Booking getBookingById(Long id) {
//        return bookingRepository.findById(id).orElseThrow();
//    }

    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

//    public Booking updateBookingStatus(Long bookingId, BookingStatus status) {
//        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
//        booking.setStatus(status);
//        return bookingRepository.save(booking);
//    }

//    public void deleteBooking(Long id) {
//        bookingRepository.deleteById(id);
//    }

//    public Booking updateBooking(Long id, Booking booking) {
//        Booking existingBooking = bookingRepository.findById(id).orElse(null);
//        if (existingBooking != null) {
//            existingBooking.setBookingDate(booking.getBookingDate());
//            existingBooking.setTotalPrice(booking.getTotalPrice());
//            existingBooking.setStatus(booking.getStatus());
//            existingBooking.setBookingType(booking.getBookingType());
//            existingBooking.setPayment(booking.getPayment());
//            existingBooking.setBookingDetails(booking.getBookingDetails());
//            existingBooking.setPromotion(booking.getPromotion());
//            return bookingRepository.save(existingBooking);
//        }
//        return null;
//    }

//    public Booking getBookingByPaymentId(Long paymentId) {
//        return bookingRepository.findByPaymentId(paymentId);
//    }
//
//    public Booking getBookingByPromotionId(Long promotionId) {
//        return bookingRepository.findByPromotionId(promotionId);
//    }
//
//    public List<Booking> getBookingByBookingType(String bookingType) {
//        return bookingRepository.findByBookingType(bookingType);
//    }
//    public List<Booking> getBookingByBookingDate(String bookingDate) {
//        return bookingRepository.findByBookingDate(bookingDate);
//    }
//
//    public List<Booking> getBookingByBookingStatus(String bookingStatus) {
//        return bookingRepository.findByBookingStatus(bookingStatus);
//    }
//
//    public List<Booking> getBookingByTotalPrice(Double totalPrice) {
//        return bookingRepository.findByTotalPrice(totalPrice);
//    }


}
