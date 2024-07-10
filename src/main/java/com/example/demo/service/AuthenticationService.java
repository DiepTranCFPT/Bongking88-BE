package com.example.demo.service;

import com.example.demo.eNum.AccoutStatus;
import com.example.demo.eNum.Role;
import com.example.demo.entity.Account;
import com.example.demo.entity.Wallet;
import com.example.demo.exception.AuthException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.EmailDetail;
import com.example.demo.model.Request.*;
import com.example.demo.model.Response.AccountResponse;
import com.example.demo.respository.AuthenticationRepository;
import com.example.demo.respository.WalletRepository;
import com.example.demo.utils.AccountUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    AccountUtils accountUtils;


    private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());

    public Account register(RegisterRequest registerRequest) {
        Account account = new Account();
        account.setName(registerRequest.getName());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setPhone(registerRequest.getPhone());
        account.setEmail(registerRequest.getEmail());
        account.setRole(Role.CUSTOMER);
        account.setStatus(AccoutStatus.ACTIVE);

        Wallet wallet = new Wallet();
        wallet.setAccount(account);
        wallet.setAmount(0);
//        wallet = walletRepository.save(wallet);
        account.setWallet(wallet);


        try {
            account = authenticationRepository.save(account);
        }catch (DataIntegrityViolationException e ) {
            throw new AuthException("Duplicate");
        }


        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(registerRequest.getEmail());
        emailDetail.setSubject("Thank you for registering.");
        emailDetail.setName(registerRequest.getName());
        emailDetail.setLink("http://booking88.online");
        emailService.sendMailTemplate(emailDetail);

        return account;
    }

    public AccountResponse login(LoginRequest loginRequest) {
        var account = authenticationRepository
                .findByEmail(loginRequest.getEmail());
        if (account == null) {
            throw new AuthException("Account not found with email: " + loginRequest.getEmail());
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) throw new AuthException("Wrong Id Or Password");


            if( account.getStatus().equals("DELETED")){
                throw new AuthException("account Deleted");
            }
        String token = tokenService.generateToken(account);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(account.getId());
        accountResponse.setEmail(account.getEmail());
        accountResponse.setToken(token);
        accountResponse.setRole(account.getRole());
        accountResponse.setName(account.getName());
        accountResponse.setPhone(account.getPhone());
        accountResponse.setWallet(account.getWallet());
        if(account.getLocation() != null)
        accountResponse.setIdLocation(account.getLocation().getId());

        return accountResponse;
    }

    public AccountResponse loginGoogle(LoginGoogleRequest loginGoogleRequest) {
        AccountResponse accountResponse = new AccountResponse();
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(loginGoogleRequest.getToken());
            String email = firebaseToken.getEmail();
            Account account = authenticationRepository.findByEmail(email);

            if (account == null) {
                account = new Account();
                account.setName(firebaseToken.getName());
                account.setEmail(firebaseToken.getEmail());
                account.setRole(Role.CUSTOMER);
                account.setStatus(AccoutStatus.ACTIVE);// Set the role directly
                Wallet wallet = new Wallet();
                wallet.setAccount(account);
                wallet.setAmount(0);
                account.setWallet(wallet);
                account = authenticationRepository.save(account);
            }else{
               if( account.getStatus().equals("DELETED")){
                  throw new AuthException("account Deleted");
               }
            }


            accountResponse.setId(account.getId());
            accountResponse.setName(account.getName());
            accountResponse.setEmail(account.getEmail());
            accountResponse.setRole(account.getRole());
            String token = tokenService.generateToken(account);
            accountResponse.setToken(token);
            accountResponse.setWallet(account.getWallet());
            if(account.getLocation() != null)
                accountResponse.setIdLocation(account.getLocation().getId());

        } catch (FirebaseAuthException e) {
            logger.severe("Firebase authentication error: " + e.getMessage());
            throw new BadRequestException("Invalid Google token");
        }

        return accountResponse;
    }

    public List<Account> all() {
        List<Role> list = new ArrayList<>();
        list.add(Role.CLUB_OWNER);
        list.add(Role.CUSTOMER);
        return authenticationRepository.findByRoleIn(list);
    }

    public List<Account> allOwner() {
        List<Role> list = new ArrayList<>();
        list.add(Role.CLUB_OWNER);
        List<Account> accounts = authenticationRepository.findByRoleIn(list).stream().filter(acc -> acc.getLocation() == null).toList();
        return accounts;
    }


    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Account account = authenticationRepository.findByEmail(forgotPasswordRequest.getEmail());
        if (account == null) {
            throw new BadRequestException("Account not found");
        }

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(forgotPasswordRequest.getEmail());
        emailDetail.setSubject("Reset Password for account " + forgotPasswordRequest.getEmail() + "!!!");
        emailDetail.setMsgBody(""); // You might want to add a meaningful message here
        emailDetail.setButtonValue("Reset Password");
        emailDetail.setLink("http://localhost:5173/reset-password?token=" + tokenService.generateToken(account));
        emailDetail.setName(account.getName());

        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplateForgot(emailDetail);
            }
        };

        new Thread(r).start();
    }

//    public Account getCurrentAccount() {
//        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Account account = accountUtils.getCurrentUser();
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        authenticationRepository.save(account);
    }

    public Account deleteAccount(long id) {
        Account account = authenticationRepository.findById(id).orElseThrow(() -> new AuthException("Can not find account"));;
        account.setStatus(AccoutStatus.DELETED);
        return authenticationRepository.save(account);
    }

    public Account updateAccount(UpdateRequest updateRequest, Long id) {
            Account account = authenticationRepository.findById(id).orElseThrow(() -> new AuthException("Can not find account"));
            account.setName(updateRequest.getName());
            account.setPhone(updateRequest.getPhone());
            account.setEmail(updateRequest.getEmail());
            try {
                account =  authenticationRepository.save(account);

            }catch (DataIntegrityViolationException e ) {
                throw new AuthException("Duplicate");
            }
        return account;
    }
    public Account findById(Long id) {
        Account account = authenticationRepository.findById(id).orElse(null);
        if (account == null) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        return account;
    }

}
