package com.AlTaraf.Booking.Payload.request.Ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsResponseDto {
    private Long id;
    private Long unitId;
    private Long userId;
    private String imagePath;
}
