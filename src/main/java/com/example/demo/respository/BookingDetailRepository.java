package com.example.demo.respository;

import com.example.demo.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailRepository extends JpaRepository<BookingDetail ,Long> {
}
