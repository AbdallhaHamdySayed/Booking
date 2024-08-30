package com.AlTaraf.Booking.Payload.request.Ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsRequestDto {
    private Long unitId;
    private Long userId;
}
