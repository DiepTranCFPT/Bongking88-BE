package com.example.demo.respository;

import com.example.demo.eNum.CourtStatus;
import com.example.demo.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CourtRepository extends JpaRepository<Court,Long> {
    List<Court> findByLocationIdAndStatus(long id, CourtStatus status);

    List<Court> findByLocationId(long id);

//    List<Court> findByIdNotIn(List<Long> idCourts);

    @Query("SELECT c FROM Court c WHERE (:idCourts IS NULL OR c.id NOT IN :idCourts) AND c.location.id = :locationId")
    List<Court> findByIdNotInAndLocationId(@Param("idCourts") List<Long> idCourts, @Param("locationId") Long locationId);
}
