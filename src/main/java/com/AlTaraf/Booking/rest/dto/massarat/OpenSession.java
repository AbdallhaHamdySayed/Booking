package com.AlTaraf.Booking.rest.dto.massarat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenSession {
    private Double amount;
    private String identityCard;
    private String transactionId;
    private Integer onlineOperation;
}
