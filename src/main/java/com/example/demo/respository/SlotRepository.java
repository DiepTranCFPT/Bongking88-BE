package com.example.demo.respository;

import com.example.demo.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByTime(String time);
    List<Slot> findByPrice(Double price);


}
