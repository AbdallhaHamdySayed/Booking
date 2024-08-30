package com.AlTaraf.Booking.Payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseFlag {
    private int statusCode;
    private String message;
    private Boolean flag;
}