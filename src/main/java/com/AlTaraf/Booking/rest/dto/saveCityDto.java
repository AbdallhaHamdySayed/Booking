package com.AlTaraf.Booking.rest.dto;

import java.util.List;

public class saveCityDto {

    private String cityName;

    private String arabicCityName;

    private List<String> regionNames;

    public saveCityDto(Long id, String cityName, String arabicCityName) {
//        this.id = id;
        this.cityName = cityName;
        this.arabicCityName = arabicCityName;
    }

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
