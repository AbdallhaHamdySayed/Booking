package com.AlTaraf.Booking.Security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  private String phone;
  private String password;
  private String deviceToken;
  private Boolean stayLoggedIn;

}
