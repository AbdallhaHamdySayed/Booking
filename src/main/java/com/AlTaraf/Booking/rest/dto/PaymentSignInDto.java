package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSignInDto {
    private Integer userId;
    private String pin;
    private Integer providerId;
    private Integer authUserType;
}
