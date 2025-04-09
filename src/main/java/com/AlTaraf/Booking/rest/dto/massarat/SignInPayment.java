package com.AlTaraf.Booking.rest.dto.massarat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInPayment {
    private Integer userId;
    private String pin;
    private Integer providerId;
    private Integer authUserType;
}
