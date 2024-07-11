package com.example.demo.respository;

import com.example.demo.eNum.CourtSlotStatus;
import com.example.demo.entity.CourtSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourtSlotRepository extends JpaRepository<CourtSlot, Long> {

    List<CourtSlot> findBySlotIdAndDate(long id, String date);

    List<CourtSlot> findByAccountIdAndStatus(long accountId, CourtSlotStatus status);
    List<CourtSlot> findByStatusAndDate(CourtSlotStatus status, String date);

//    List<CourtSlot> findByLo(long id, CourtSlotStatus status);

    @Query("SELECT cs FROM CourtSlot cs WHERE cs.court.location.id = :locationId AND cs.status <> 'UNSUCCESSFUL' AND cs.date = :currentDate")
    List<CourtSlot> findByLocationAndStatusAndDate(@Param("locationId") Long locationId, @Param("currentDate") String currentDate);


}
