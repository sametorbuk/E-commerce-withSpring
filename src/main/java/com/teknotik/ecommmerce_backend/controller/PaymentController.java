package com.teknotik.ecommmerce_backend.controller;

import com.iyzipay.model.Payment;
import com.teknotik.ecommmerce_backend.service.PaymentService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    Dotenv dotenv = Dotenv.load();

    public PaymentController() {
        this.paymentService = new PaymentService(dotenv.get("IYZICO_API_KEY"), dotenv.get("IYZICO_SECRET_KEY"),dotenv.get("IYZICO_BASE_URL"));
    }

    @PostMapping("/create")
    public Payment createPayment() {
        Payment payment = paymentService.createPayment();
        return payment;
    }
}
