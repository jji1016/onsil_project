package com.onsil.onsil.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentPageController {
    @GetMapping("/payment")
    public String paymentPage() {
        return "payment/payment";
    }
}