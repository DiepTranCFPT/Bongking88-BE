package com.example.demo.respository;

import com.example.demo.eNum.PromotionStatus;
import com.example.demo.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion> findByLocationId(long id);

    Promotion findByIdAndStatus(long id ,PromotionStatus status);
    
}
