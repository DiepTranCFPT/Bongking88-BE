package com.example.demo.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name="api")
@RequestMapping("api/court")
@CrossOrigin("*")
public class UserApi {
}
