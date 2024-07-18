package com.example.demo.entity;

import com.example.demo.eNum.AccoutStatus;
import com.example.demo.eNum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
public class Account  {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   long id;
   String name;

   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   String password;

   @Column(unique = true)
   String phone;

   @Enumerated(EnumType.STRING)
   AccoutStatus status;

   @Column(unique = true)
   String email;

   @Enumerated(EnumType.STRING)
   Role role;

   @OneToOne(mappedBy = "owner")
   @JsonIgnore
   Location location;

   @ManyToOne
   @JoinColumn(name = "locationStaff_id")
           @JsonIgnore
   Location locationStaff;

   @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
   @JsonIgnore
   List<Booking> booking;

   @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "wallet_id")
   @JsonIgnore
   Wallet wallet;

   @OneToMany(mappedBy = "account")
   @JsonIgnore
   List<CourtSlot> courtSlots;


   private boolean enable;

   private String verificationCode;

}
