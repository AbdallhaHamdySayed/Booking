package com.AlTaraf.Booking.Dto.ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsResponse {
    private Long adsId;
    private String message;
}
