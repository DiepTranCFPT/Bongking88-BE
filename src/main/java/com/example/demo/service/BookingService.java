package com.example.demo.service;

import com.example.demo.eNum.BookingStatus;
import com.example.demo.entity.*;
import com.example.demo.respository.BookingRepository;
import com.example.demo.respository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private VNPAYService vnpayService;
    private PaymentRepository paymentRepository;
    private SlotService slotService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, VNPAYService vnpayService,
                          PaymentRepository repository, SlotService slotService) {
        this.bookingRepository = bookingRepository;
        this.vnpayService = vnpayService;
        this.paymentRepository = repository;
        this.slotService = slotService;
    }

    public Booking createBooking(Booking booking) throws Exception {

        if (booking != null) {
            double totalPrince = 0;
            List<BookingDetail> bookingDetails = booking.getBookingDetails();
            List<CourtSlot> courtSlots = new ArrayList<>();
            if(bookingDetails != null) {
                for (BookingDetail bookingDetail : bookingDetails) {
                   courtSlots = bookingDetail.getCourtSlots();

                    for (CourtSlot courtSlot : courtSlots) {
                        List<Slot> slots = courtSlot.getSlot();

                        for (Slot slot : slots) {
                            if(!slotService.CheckSlot(slot.getSlotId())){
                                totalPrince += slot.getPrice();
                                slotService.UpdateStatus(slot.getSlotId());
                            }
                        }
                    }
                }
            }

            booking.setTotalPrice(String.valueOf(totalPrince));
            booking.setStatus(BookingStatus.PENDING);

            Payment payment = new Payment();
            payment.setAmount(booking.getTotalPrice());
            payment.setStatus(BookingStatus.PENDING);
            payment.setTransactionId(UUID.randomUUID().toString());
            payment.setBooking(booking);



            booking.setPayment(payment);



        }

        booking = bookingRepository.save(booking);
        // Create a payment for the booking
        Payment payment = new Payment();
        payment.setAmount(booking.getTotalPrice());
        payment.setStatus(BookingStatus.PENDING);
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setBooking(booking);

        payment = paymentRepository.save(payment);

        // Generate VNPAY payment URL
        String vnpayUrl = vnpayService.createUrl(payment.getAmount(), payment.getTransactionId());

        // Set the payment URL to the booking

        booking.setPayment(payment);

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public Booking updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        if (existingBooking != null) {
            existingBooking.setBookingDate(booking.getBookingDate());
            existingBooking.setTotalPrice(booking.getTotalPrice());
            existingBooking.setStatus(booking.getStatus());
            existingBooking.setBookingType(booking.getBookingType());
            existingBooking.setPayment(booking.getPayment());
            existingBooking.setBookingDetails(booking.getBookingDetails());
            existingBooking.setPromotion(booking.getPromotion());
            return bookingRepository.save(existingBooking);
        }
        return null;
    }

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
