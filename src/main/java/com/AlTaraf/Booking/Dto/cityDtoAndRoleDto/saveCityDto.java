package com.AlTaraf.Booking.Dto.cityDtoAndRoleDto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

public class saveCityDto {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String cityName;

    private String arabicCityName;

    private List<String> regionNames;

    public saveCityDto(Long id, String cityName, String arabicCityName) {
//        this.id = id;
        this.cityName = cityName;
        this.arabicCityName = arabicCityName;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getArabicCityName() {
        return arabicCityName;
    }

    public void setArabicCityName(String arabicCityName) {
        this.arabicCityName = arabicCityName;
    }
}
