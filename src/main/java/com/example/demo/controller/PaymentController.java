package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.service.VNPAYService;
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
@SecurityRequirement(name="api")
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    private VNPAYService vnPayService;

    @GetMapping({"", "/"})
    public String home() {
        return "createOrder";
    }

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") String orderTotal) throws Exception {
        String vnpayUrl = vnPayService.createUrl(orderTotal);
        return vnpayUrl;
    }


    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public ResponseEntity<Void> paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        HttpHeaders headers = new HttpHeaders();
        if (paymentStatus == 1) {
            // URL thành công
            String successUrl = "http://booking88.online/Payment_Success";
            headers.setLocation(URI.create(successUrl));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            // URL thất bại
            String failureUrl = "http://booking88.online/Payment_Failed";
            headers.setLocation(URI.create(failureUrl));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }
}