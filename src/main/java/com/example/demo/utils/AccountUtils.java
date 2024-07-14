package com.example.demo.utils;

import com.example.demo.entity.Account;
import com.example.demo.respository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccountUtils {

    private static AuthenticationRepository userRepository;

    @Autowired
    public void setUserRepository(AuthenticationRepository userRepository) {
        AccountUtils.userRepository = userRepository;
    }

    public static Account getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Account user = userRepository.findByEmail(email);
        return user;
    }
}
