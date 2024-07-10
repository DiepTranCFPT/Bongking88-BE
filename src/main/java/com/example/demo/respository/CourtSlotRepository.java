package com.example.demo.respository;

import com.example.demo.entity.CourtSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourtSlotRepository extends JpaRepository<CourtSlot, Long> {

    List<CourtSlot> findBySlotIdAndDate(long id, String date);

}
