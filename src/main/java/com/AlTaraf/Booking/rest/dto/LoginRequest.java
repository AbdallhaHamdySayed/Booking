package com.AlTaraf.Booking.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    private String phone;
    @NotBlank
    private String password;
    private boolean stayLoggedIn;
    private Set<String> roles;

    private String deviceToken;
}