package com.example.demo.controller;


import com.example.demo.eNum.AccoutStatus;
import com.example.demo.eNum.CourtStatus;
import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import com.example.demo.entity.Court;
import com.example.demo.entity.Location;
import com.example.demo.entity.Wallet;
import com.example.demo.model.Request.ClubRequest;
import com.example.demo.model.Request.CourtResquest;
import com.example.demo.respository.AuthenticationRepository;
import com.example.demo.respository.CourtRepository;
import com.example.demo.service.CourtService;
import com.example.demo.service.LocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name="api")
@RequestMapping("api/create")
@CrossOrigin("*")
public class UserApi {

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    LocationService locationService;

    @Autowired
    CourtRepository courtRepository;

    @Autowired
    CourtService courtService;
    @GetMapping
    public void create  (){
        Account account = new Account();
        account.setEmail("string@gmail.com");
        account.setPassword(passwordEncoder.encode("string"));
        account.setRole(Role.CUSTOMER);
        account.setPhone("1");
        account.setName("string");
        account.setStatus(AccoutStatus.ACTIVE);
        Wallet wallet = new Wallet();
        wallet.setAccount(account);
        wallet.setAmount(0);
        account.setWallet(wallet);

        Account owner1 = new Account();
        owner1.setEmail("owner1@gmail.com");
        owner1.setPassword(passwordEncoder.encode("owner1"));
        owner1.setRole(Role.CLUB_OWNER);
        owner1.setPhone("2");
        owner1.setName("string2");
        owner1.setStatus(AccoutStatus.ACTIVE);
        Wallet wallet1 = new Wallet();
        wallet1.setAccount(owner1);
        wallet1.setAmount(0);
        owner1.setWallet(wallet1);


        Account owner2 = new Account();
        owner2.setEmail("owner2@gmail.com");
        owner2.setPassword(passwordEncoder.encode("owner2"));
        owner2.setRole(Role.CLUB_OWNER);
        owner2.setPhone("4");
        owner2.setName("string4");
        owner2.setStatus(AccoutStatus.ACTIVE);
        Wallet wallet2 = new Wallet();
        wallet2.setAccount(owner2);
        wallet2.setAmount(0);
        owner2.setWallet(wallet2);


        Account account2 = new Account();
        account2.setEmail("admin@gmail.com");
        account2.setPassword(passwordEncoder.encode("admin"));
        account2.setRole(Role.ADMIN);
        account2.setPhone("3");
        account2.setName("string3");
        account2.setStatus(AccoutStatus.ACTIVE);
        Wallet wallet3 = new Wallet();
        wallet3.setAccount(account2);
        wallet3.setAmount(0);
        account2.setWallet(wallet3);


        account =   authenticationRepository.save(account);
        owner1 = authenticationRepository.save(owner1);

        owner2 = authenticationRepository.save(owner2);
        account2 = authenticationRepository.save(account2);

        ClubRequest clubRequest = new ClubRequest();
        clubRequest = clubRequest.builder()
                .address("vinhome grand park")
                .hotline("0912345678")
                .photo("https://sonsanepoxy.vn/wp-content/uploads/2023/07/San-cau-long-2.jpg")
                .description("hello")
                .openingTime(6)
                .closingTime(21)
                .ownerId(owner1.getId())
                .priceSlot(50000)
                .name("location1").build();
        Location location1 = locationService.createNewClub(clubRequest);

        ClubRequest clubRequest1 = new ClubRequest();
        clubRequest1 = clubRequest1.builder()
                .address("FPT xinh dep")
                .hotline("0512345678")
                .photo("https://sonsanepoxy.vn/wp-content/uploads/2023/07/San-cau-long.jpg")
                .description("hi")
                .openingTime(9)
                .closingTime(23)
                .ownerId(owner2.getId())
                .priceSlot(30000)
                .name("location2").build();

        Location location2 =  locationService.createNewClub(clubRequest1);

        for(int i = 0; i < 5;i++){
            Court court = new Court();
            court.setName("sân " + i);
            court.setStatus(CourtStatus.ACTIVE);
            court.setLocation(location1);
            court.setImage("");
            courtRepository.save(court);
        }

        for(int i = 5; i < 11;i++){
            Court court = new Court();
            court.setName("sân " + i);
            court.setStatus(CourtStatus.ACTIVE);
            court.setLocation(location2);
            court.setImage("");
            courtRepository.save(court);
        }




    }

}
