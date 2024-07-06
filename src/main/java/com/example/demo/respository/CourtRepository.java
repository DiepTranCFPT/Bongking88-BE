package com.example.demo.respository;

import com.example.demo.eNum.CourtStatus;
import com.example.demo.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourtRepository extends JpaRepository<Court,Long> {
    List<Court> findByLocationIdAndStatus(long id, CourtStatus status);

    List<Court> findByLocationId(long id);

}
