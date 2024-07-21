package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.model.Request.*;
import com.example.demo.model.Response.AccountResponse;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@SecurityRequirement(name = "api")
@CrossOrigin("*")
@RequestMapping("api")
public class AuthenticationAPI {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
            Account account = authenticationService.register(registerRequest);
            return ResponseEntity.ok(account);
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verifyUser(@RequestParam("code") String code) {
        boolean verified = authenticationService.verify(code);
        if (verified) {
            // URL thành công
            String successUrl = "http://booking88.online/verify_success";
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(successUrl));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            // URL thất bại
            String failureUrl = "http://booking88.online/verify_failed";
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(failureUrl));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        AccountResponse account = authenticationService.login(loginRequest);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/login-google")
    public ResponseEntity<AccountResponse> loginGg(@RequestBody LoginGoogleRequest loginGoogleRequest) {
        return ResponseEntity.ok(authenticationService.loginGoogle(loginGoogleRequest));
    }

    @PostMapping("/forgot-password")
    public void forgotpassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authenticationService.forgotPassword(forgotPasswordRequest);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(
            @RequestParam("token") String token,@RequestBody ResetPasswordRequest resetPasswordRequest) {

        if(authenticationService.resetPassword(resetPasswordRequest)==1){
            if(token.equals(resetPasswordRequest.getToken())){
                String successUrl = "http://booking88.online/verify_success";
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create(successUrl));
                return new ResponseEntity<>(headers, HttpStatus.FOUND);}

        }else {

            String successUrl = "http://booking88.online/verify_failed";
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(successUrl));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);

        }
        return null;
    }



    }
