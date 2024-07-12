package com.example.demo.entity;

import com.example.demo.eNum.AccoutStatus;
import com.example.demo.eNum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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
   @JsonIgnore
   @JoinColumn(name = "locationStaff_id")
   Location locationStaff;

   @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
   @JsonIgnore
   List<Booking> booking;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "wallet_id")
   Wallet wallet;

   @OneToMany(mappedBy = "account")
   @JsonIgnore
   List<CourtSlot> courtSlots;




}
