package com.AlTaraf.Booking.Controller.Payment;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PaymentWebSocketController {

    @MessageMapping("/payment-status")
    @SendTo("/topic/payment")
    public String sendPaymentStatus(String status) {
        return status;
    }
}