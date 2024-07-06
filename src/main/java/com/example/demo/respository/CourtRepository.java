package com.example.demo.respository;

import com.example.demo.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourtRepository extends JpaRepository<Court,Long> {
    
}
