package com.example.demo.respository;

import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthenticationRepository extends JpaRepository<Account, Long>
{   // dua ra daatabase
    Account findByEmail(String email);
    List<Account> findByRole(Role role);

}
