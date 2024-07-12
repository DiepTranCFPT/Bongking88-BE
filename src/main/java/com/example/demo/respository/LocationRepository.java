package com.example.demo.respository;


import com.example.demo.eNum.ClubStatus;
import com.example.demo.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location,Long> {


    List<Location>  findByNameContainingOrAddressContaining(String name, String address);
    Optional<Location> findByIdAndStatus(long id, ClubStatus status);
    Location findByOwnerId(long id);

}