package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.model.EmailDetail;
import com.example.demo.model.Request.*;
import com.example.demo.model.Response.AccountResponse;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class AuthenticationAPI {
    @Autowired
    AuthenticationService authenticationService;///git branch
    @Autowired
    EmailService emailService;



    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        try {
            Account account = authenticationService.register(registerRequest);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về phản hồi lỗi nếu có
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }


    @PostMapping("/send-mail")
    public void sendMail() {
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient("trancaodiepnct@gmail.com");
        emailDetail.setSubject("test123");
        emailDetail.setMsgBody("aaa");
        emailService.sendMailTemplate(emailDetail);
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
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPassword(resetPasswordRequest);
    }
}
