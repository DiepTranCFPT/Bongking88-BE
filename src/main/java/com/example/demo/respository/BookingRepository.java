package com.example.demo.respository;

import com.example.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerId(Long customerId);
//    Booking findByPaymentId(Long paymentId);
//    Booking findByPromotionId(Long promotionId);
//    List<Booking> findByBookingType(String bookingType);
//    List<Booking> findByBookingStatus(String bookingStatus);
//    List<Booking> findByTotalPrice(Double totalPrice);
//    List<Booking> findByBookingDate(String bookingDate);

}

