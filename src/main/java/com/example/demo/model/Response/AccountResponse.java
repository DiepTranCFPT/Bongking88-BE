package com.example.demo.model.Response;

import com.example.demo.entity.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse extends Account {

    private String token;

}
