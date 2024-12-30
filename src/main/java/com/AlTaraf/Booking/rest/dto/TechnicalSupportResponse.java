package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalSupportResponse {
    private Long id;
    private FileForProfileDTO fileForProfileDTO;
    private String name;
    private String email;
    private String message;
    private Boolean seen;
    private Long userId;
    private Duration elapsedTime;
}
