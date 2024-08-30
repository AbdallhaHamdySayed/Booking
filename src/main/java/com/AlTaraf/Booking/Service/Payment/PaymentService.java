package com.AlTaraf.Booking.Service.Payment;

import com.AlTaraf.Booking.Dto.payment.PaymentDto;
import org.springframework.http.ResponseEntity;

public interface PaymentService {

    ResponseEntity<?> sendTransactionRequest(Long userId, String customRef);
    ResponseEntity<?> initialPayment(Double amount, String phone, String email);

    ResponseEntity<?> backEndUrl(PaymentDto paymentDto);
}
