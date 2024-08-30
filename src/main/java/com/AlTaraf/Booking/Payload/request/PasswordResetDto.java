package com.AlTaraf.Booking.Payload.request;

import lombok.Data;

@Data
public class PasswordResetDto {
    private String newPassword;

    public PasswordResetDto() {
    }

    public PasswordResetDto(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}