package com.AlTaraf.Booking.Dto.packageAds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageAdsEditDTO {
    private String arabicName;
    private Double price;
    private int numberAds;
}
