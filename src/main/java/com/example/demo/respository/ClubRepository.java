package com.example.demo.respository;


import com.example.demo.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Location,Long> {
    Location findByName(String name);
}