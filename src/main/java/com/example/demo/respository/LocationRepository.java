package com.example.demo.respository;


import com.example.demo.eNum.ClubStatus;
import com.example.demo.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Long> {
//    List<Location> findByStatus();

    List<Location>  findByNameContainingOrAddressContaining(String name, String address);
}