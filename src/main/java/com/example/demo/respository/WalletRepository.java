package com.example.demo.respository;

import com.example.demo.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
}
