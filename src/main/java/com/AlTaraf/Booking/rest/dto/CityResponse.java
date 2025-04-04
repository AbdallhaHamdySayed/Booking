package com.AlTaraf.Booking.rest.dto;

public class CityResponse {
    private CityDto cityDto;
    private ApiResponse apiResponse;

    public CityResponse(CityDto cityDto, ApiResponse apiResponse) {
        this.cityDto = cityDto;
        this.apiResponse = apiResponse;
    }

    public void setCityDto(CityDto cityDto) {
        this.cityDto = cityDto;
    }

    public void setApiResponse(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

    public CityDto getCityDto() {
        return cityDto;
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }
}
