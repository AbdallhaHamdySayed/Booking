package com.AlTaraf.Booking.Payload.request.TechnicalSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalSupportRequest {
    private String name;
    private String email;
    private String message;
    private Long userId;
}