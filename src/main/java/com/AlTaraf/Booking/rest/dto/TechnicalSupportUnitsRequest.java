package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalSupportUnitsRequest {
    private String name;
    private String email;
    private String message;
    private Long userId;
    private Long unitId;
}