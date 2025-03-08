package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOpenSessionDto {
    private Double amount;
    private String identityCard;
    private String transactionId;
    private Integer onlineOperation;
}
