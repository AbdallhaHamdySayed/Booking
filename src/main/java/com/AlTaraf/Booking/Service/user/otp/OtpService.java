package com.AlTaraf.Booking.Service.user.otp;

import org.springframework.http.ResponseEntity;

public interface OtpService {
    ResponseEntity<?> validateOtp(String recipient, int otp, String acceptLanguageHeader);

    Boolean checkValidateOtp(String recipient, int otp);

    ResponseEntity<?> sendOtp(String recipient, String acceptLanguageHeader);

    ResponseEntity<?> sendOtpViaWhatsApp(String recipient, String acceptLanguageHeader);
}
