package com.example.demo.controller;


import com.example.demo.service.VNPAYService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/vnpay-payment-return")
    public ResponseEntity<String> paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);



        return paymentStatus == 1 ? ResponseEntity.ok("ordersuccess") : ResponseEntity.ok("orderfail");
    }

}