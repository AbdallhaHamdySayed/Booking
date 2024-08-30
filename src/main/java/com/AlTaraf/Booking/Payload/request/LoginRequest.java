package com.AlTaraf.Booking.Payload.request;

import com.AlTaraf.Booking.Entity.Role.Role;
import jakarta.validation.constraints.*;
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
//    private Role role;
    private Set<String> roles;

    private String deviceToken;
//    private Boolean isClientFlag;
}