package com.AlTaraf.Booking.rest.dto;

import com.AlTaraf.Booking.database.entity.TypesOfEventHalls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventHallsResponse {
    private Long unitId;
    private String nameUnit;
    private TypesOfEventHalls typesOfEventHalls;
    private List<String> imagePaths;
    private String videoPaths;
    private String description;
    private int capacityHalls;
    private CityDto cityDto;
    private RegionDto regionDto;
    private Set<FeatureForHallsDto> featuresHallsDto;
    private Set<AvailablePeriodsDto> availablePeriodsHallsDto;
    private int oldPriceHall;
    private int newPriceHall;
    private Double latForMapping;
    private Double longForMapping;
    private int price;
    private int oldPrice;
    private String evaluationName;
    private String evaluationArabicName;
    private String dateOfArrival;
    private String departureDate;
}
