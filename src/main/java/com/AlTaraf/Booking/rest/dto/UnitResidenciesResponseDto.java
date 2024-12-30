package com.AlTaraf.Booking.rest.dto;

import com.AlTaraf.Booking.database.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
// Residencies
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitResidenciesResponseDto {

    private Long unitId;
    private Long unitTypeId;
    private List<String> imagePaths;
    private String videoPaths;
    private String nameUnit;
    private String description;
    private CityDto cityDto;
    private RegionDto regionDto;
    private AccommodationType accommodationType;
    private List<FileForUnit> images;
    private HotelClassification hotelClassification;
    private TypeOfApartment typeOfApartment;
    private Set<RoomAvailable> roomAvailables;
    private Set<Feature> features;
    private Set<SubFeature> subFeatures;
    private Set<AvailableArea> availableAreas;

    private Double latForMapping;
    private Double longForMapping;
    private String evaluationName;
    private String evaluationArabicName;
    private String dateOfArrival;
    private String departureDate;
}
