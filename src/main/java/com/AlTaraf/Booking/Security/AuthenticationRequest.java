package com.AlTaraf.Booking.Security;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  @NotBlank
  private String phone;
  @NotBlank
  private String password;
  private Set<String> roles;
  private String deviceToken;
  private Boolean stayLoggedIn;

}
