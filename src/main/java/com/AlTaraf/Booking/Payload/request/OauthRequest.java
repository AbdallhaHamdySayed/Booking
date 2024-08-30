package com.AlTaraf.Booking.Payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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