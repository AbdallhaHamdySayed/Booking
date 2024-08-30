package com.AlTaraf.Booking.Payload.response;

public class AuthenticationResponse {
    private int statusCode;
    private String message;
    private String otp;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(int statusCode, String message, String otp) {
        this.statusCode = statusCode;
        this.message = message;
        this.otp = otp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
