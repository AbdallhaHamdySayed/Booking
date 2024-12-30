package com.AlTaraf.Booking.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class OauthRequest {
    @NotBlank
    private String phone;
    @NotBlank
    private String password;
    private boolean stayLoggedIn;
    private Set<String> roles;

    public OauthRequest() {
        this.password = "defaultPassword";
    }
}