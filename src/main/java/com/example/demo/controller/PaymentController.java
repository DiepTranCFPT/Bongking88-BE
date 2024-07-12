package com.example.demo.controller;


import com.example.demo.service.VNPAYService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name="api")
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    private VNPAYService vnPayService;

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") String orderTotal) throws Exception {
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createUrl(orderTotal);
        return vnpayUrl;
    }

}