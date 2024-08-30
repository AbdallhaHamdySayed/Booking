package com.AlTaraf.Booking.Payload.response;

public class CheckApiResponse {
    private int statusCode;
    private String message;
    private Boolean check;

    public CheckApiResponse() {
    }

    public CheckApiResponse(int statusCode, String message, Boolean check) {
        this.statusCode = statusCode;
        this.message = message;
        this.check = check;
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

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
