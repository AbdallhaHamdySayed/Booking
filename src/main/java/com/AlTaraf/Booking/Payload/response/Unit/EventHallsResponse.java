package com.AlTaraf.Booking.Payload.response.Unit;

import com.AlTaraf.Booking.Dto.Unit.FeatureForHalls.FeatureForHallsDto;
import com.AlTaraf.Booking.Dto.Unit.availablePeriodsHalls.AvailablePeriodsDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.unit.typesOfEventHalls.TypesOfEventHalls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
// Event Halls
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
