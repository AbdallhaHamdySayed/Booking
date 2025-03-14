package com.AlTaraf.Booking.rest.controller;

import com.AlTaraf.Booking.rest.dto.PaymentDto;
import com.AlTaraf.Booking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/initiate_payment")
    public ResponseEntity<?> initiatePayment(
            @RequestParam Double amount,
            @RequestParam String phone,
            @RequestParam(required = false) String email) {

        return paymentService.initialPayment(amount, phone, email);
    }

    @PostMapping("/Transaction/{userId}")
    public ResponseEntity<?> transaction(
            @PathVariable Long userId,
            @RequestParam String custom_ref
    ) {
        return paymentService.sendTransactionRequest(userId, custom_ref);
    }

    @PostMapping("/back-end-url")
    public ResponseEntity<?> backEndUrl(@RequestBody PaymentDto paymentDto) {

        return paymentService.backEndUrl(paymentDto);
    }
}