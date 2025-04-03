package com.maverickdevs.expensebuddy.controllers;

import com.maverickdevs.expensebuddy.dto.request.PaymentRequestDTO;
import com.maverickdevs.expensebuddy.entities.Payment;
import com.maverickdevs.expensebuddy.services.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    @Autowired
    public PaymentController(PaymentServiceImpl paymentService){
        this.paymentService = paymentService;
    }
    @PostMapping("/addpayment")
    public ResponseEntity<?> addPayment(@RequestBody PaymentRequestDTO paymentRequestDTO){
        return ResponseEntity.ok(paymentService.addPayment(paymentRequestDTO));
    }

}
