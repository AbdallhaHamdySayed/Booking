package com.AlTaraf.Booking.Payload.response.Unit;

import com.AlTaraf.Booking.Dto.Unit.FeatureForHalls.FeatureForHallsDto;
import com.AlTaraf.Booking.Dto.Unit.availablePeriodsHalls.AvailablePeriodsDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.Comment;
import com.AlTaraf.Booking.Entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.Entity.unit.typesOfApartments.TypeOfApartment;
import com.AlTaraf.Booking.Entity.unit.typesOfEventHalls.TypesOfEventHalls;
import com.AlTaraf.Booking.Entity.unit.unitType.UnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

//    private int oldPriceHall;
//    private int newPriceHall;

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
    private LocalDate dateOfArrival;
    private LocalDate departureDate;
    private int commission;
    private int adultsAllowed;
    private int childrenAllowed;
    private Boolean favorite;
    private Integer totalEvaluation;
    private String deviceToken;
}
