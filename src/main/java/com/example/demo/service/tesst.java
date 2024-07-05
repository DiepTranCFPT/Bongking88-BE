//package com.example.demo.service;
//
//
//import com.example.demo.model.EmailDetail;
//import com.example.demo.utils.AccountUtils;
//import com.example.exception.exceptions.BadRequestException;
//import com.example.mapper.UserMapper;
//import com.example.model.request.LoginGoogleRequest;
//import com.example.model.request.ResetPasswordRequest;
//import com.example.model.response.AuthenticationResponse;
//import com.example.model.entity.User;
//import com.example.model.request.AuthenticationRequest;
//import com.example.model.request.LoginRequestDTO;
//import com.example.repository.UserRepository;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthException;
//import com.google.firebase.auth.FirebaseToken;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class tesst {
//
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    JWTService jwtService;
//
//   @Autowired
//    UserMapper userMapper;
//
//    @Autowired
//    AccountUtils accountUtils;
//
//    @Autowired
//    EmailService emailService;
//
//    public User register(AuthenticationRequest request) {
//        User user = userMapper.toUser(request);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRole(RoleEnum.USER);
//       try {
//           return userRepository.save(user);
//       }catch (DataIntegrityViolationException e){
//           System.out.println(e.getMessage());
//           throw new DataIntegrityViolationException("Duplicate UserName");
//       }
//    }
//
//    public AuthenticationResponse authenticate(LoginRequestDTO request){
//        var user = userRepository
//                .findByUsername(request.getUsername())
//                .orElseThrow(() -> new NullPointerException("Wrong Id Or Password"));
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new NullPointerException("Wrong Id Or Password");
//
//        AuthenticationResponse authenticationResponse = userMapper.toAuthenticationResponse(user);
//        authenticationResponse.setToken(jwtService.generateToken(user));
//
//        return authenticationResponse;
//    }
//
//
//    public AuthenticationResponse loginGoogle (LoginGoogleRequest loginGoogleRequest) {
//        try{
//            FirebaseToken decodeToken = FirebaseAuth.getInstance().verifyIdToken(loginGoogleRequest.getToken());
//            String email = decodeToken.getEmail();
//            User user = userRepository.findByEmail(email).orElseThrow();
//            if(user == null) {
//                user = new User();
//                user.setFirstName(decodeToken.getName());
//                user.setEmail(email);
//                user.setUsername(email);
//                user = userRepository.save(user);
//            }
//            AuthenticationResponse authenticationResponse = userMapper.toAuthenticationResponse(user);
//            authenticationResponse.setToken(jwtService.generateToken(user));
//            return authenticationResponse;
//        } catch (FirebaseAuthException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public void forgotPasswordRequest(String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Email Not Found"));
//
//        EmailDetail emailDetail = new EmailDetail();
//        emailDetail.setRecipient(user.getEmail());
//        emailDetail.setSubject("Reset password for account " + user.getEmail() + "!");
//        emailDetail.setMsgBody("aaa");
//        emailDetail.setButtonValue("Reset Password");
//        emailDetail.setFullName(user.getFirstName() + " " + user.getLastName());
//        emailDetail.setLink("http://jewerystorepoppy.online/reset-password?token=" + jwtService.generateToken(user));
//
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                emailService.sendMailTemplate(emailDetail);
//            }
//
//        };
//        new Thread(r).start();
//
//    }
//
//    public User resetPassword(ResetPasswordRequest resetPasswordRequest) {
//        User user = accountUtils.getCurrentUser();
//        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
//        return userRepository.save(user);
//    }
//
//
//    public String admin(){
//        return "admin";
//    }
//
//
//
//}
//
