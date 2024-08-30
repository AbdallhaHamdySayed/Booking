package com.AlTaraf.Booking.Dto.Unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDto {

    private Long unitId;
    private Long unitTypeId;
    private List<String> images; // List of image file paths
    private String video; // List of image file paths
    private String nameUnit;
    private String cityName;
    private String regionName;

    private String arabicCityName;
    private String regionArabicName;
    private int price;
    private int oldPrice;

}