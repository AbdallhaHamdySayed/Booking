package com.AlTaraf.Booking.rest.dto;

import com.AlTaraf.Booking.database.entity.City;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;

    private String arabicCityName;

    private List<RegionDto> regions = new ArrayList<>();

    public CityDto(City city) {
        this.id = city.getId();
        this.cityName = city.getCityName();
        this.arabicCityName = city.getArabicCityName();
//        city.getRegions().forEach(region -> this.regions.add(new RegionDto(region)));
    }
}
