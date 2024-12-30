package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterAds {

    private Long counterAllAds;

    private Long counterAcceptedAds;

    private Long counterRejectedAds;
}
