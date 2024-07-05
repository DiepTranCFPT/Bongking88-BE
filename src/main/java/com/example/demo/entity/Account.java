package com.example.demo.entity;

import com.example.demo.eNum.AccoutStatus;
import com.example.demo.eNum.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity // danh dau day la 1 entity
@Getter
@Setter
@ToString
public class Account  {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   long id;
   String name;

   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   String password;

   @Column(unique = true)
   String phone;

   AccoutStatus accoutStatus;

   @Column(unique = true)
   String email;

   @Enumerated(EnumType.STRING)
   Role role;







}
