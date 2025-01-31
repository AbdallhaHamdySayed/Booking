package com.AlTaraf.Booking.rest.dto;


import com.AlTaraf.Booking.database.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

// General
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitGeneralResponseDto {
    private Long unitId;
    private UnitType unitType;
    private List<String> imagePaths;
    private List<Comment> comments;
    private String videoPaths;
    private String youtubeUrl;
    private String nameUnit;
    private String description;
    private CityDtoSample cityDtoSample;
    private RegionDto regionDto;

    private AccommodationType accommodationType;
    private TypesOfEventHalls typesOfEventHalls;

//    private List<ImageDataDTO> images;

    private HotelClassification hotelClassification;
    private TypeOfApartment typeOfApartment;

    private Set<RoomAvailable> roomAvailables;

    private Set<AvailableArea> availableAreas;

    private Set<Feature> features;

    private Set<SubFeature> subFeatures;

    private int capacityHalls;

    private Set<FeatureForHallsDto> featuresHallsDto;
    private Set<AvailablePeriodsDto> availablePeriodsHallsDto;

    private List<RoomDetails> roomDetails;

    private List<RoomDetailsForAvailableArea> roomDetailsForAvailableAreaList;

    private Double latForMapping;
    private Double longForMapping;

    private int oldPriceHall;
    private int newPriceHall;

    private int ChaletOldPrice;
    private int ChaletNewPrice;

    private int apartmentOldPrice;
    private int apartmentNewPrice;

    private int loungeOldPrice;
    private int loungeNewPrice;

    private int price;
    private int oldPrice;
    private String evaluationName;
    private String evaluationArabicName;
    private String dateOfArrival;
    private String departureDate;
    private int commission;
    private int adultsAllowed;
    private int childrenAllowed;
    private Boolean favorite;
    private Integer totalEvaluation;
    private String deviceToken;
}
