package com.example.demo.respository;

import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import com.example.demo.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthenticationRepository extends JpaRepository<Account, Long>
{   // dua ra daatabase
    Account findByEmail(String email);
    List<Account> findByRole(Role role);
    List<Account> findByRoleIn(List<Role> role);
    List<Account> findAllByRoleAndAndLocationStaff(Role role, Location location);
    Account findByLocationId(Long id);

    Account findByVerificationCode(String code);


}
