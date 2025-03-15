package com.AlTaraf.Booking.service;


import com.AlTaraf.Booking.database.entity.*;
import com.AlTaraf.Booking.database.repository.*;
import com.AlTaraf.Booking.rest.dto.*;
import com.AlTaraf.Booking.rest.mapper.UnitDashboardMapper;
import com.AlTaraf.Booking.rest.mapper.UnitFavoriteMapper;
import com.AlTaraf.Booking.rest.mapper.UnitMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UnitService {

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    FileForUnitRepository fileForUnitRepository;

    @Autowired
    FileForAdsRepository fileForAdsRepository;

    @Autowired
    UnitFavoriteMapper unitFavoriteMapper;

    @Autowired
    UnitDashboardMapper unitDashboardMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    EvaluationRepository evaluationRepository;

    @Autowired
    ReservationRepository reservationsRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReserveDateRepository reserveDateRepository;

    @Autowired
    ReserveDateHallsService reserveDateHallsService;

    @Autowired
    ReserveDateRoomDetailsRepository reserveDateRoomDetailsRepository;

    @Autowired
    ReserveDateUnitService reserveDateUnitService;

    @Autowired
    RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @Autowired
    UserFavoriteUnitRepository userFavoriteUnitRepository;

    @Autowired
    RoomDetailsRepository roomDetailsRepository;

    @Autowired
    AdsService adsService;

    @Autowired
    TechnicalSupportUnitRepository technicalSupportUnitRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserFavoriteUnitService userFavoriteUnitService;

    @Autowired
    UnitMapper unitMapper;

    public Unit saveUnit(Unit unit) {
        try {
            return unitRepository.save(unit);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw e;
        }
    }

    public Page<UnitDtoFavorite> getUnitByEvaluationInOrderByEvaluationScoreDesc(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Unit> units = unitRepository.findByEvaluationInOrderByEvaluationScoreDesc(pageRequest);

        for (Unit unit : units.getContent()) {
            if (userFavoriteUnitService.existsByUserIdAndUnitId(userId,  unit.getId())){
                assert  unit.getId() != null;
                unit.setFavorite(true);
            }
        }

        return units.map(unitFavoriteMapper::toUnitFavoriteDto);
    }


    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).orElse(null);
    }

    public List<UnitDtoFavorite> getNewlyAdded(Long userId) {

        List<Unit> units = unitRepository.findByNewlyAdded();

        for (Unit unitDtoFavorite : units) {
            if (userFavoriteUnitService.existsByUserIdAndUnitId(userId,  unitDtoFavorite.getId())){
                assert unitDtoFavorite.getId() != null;
                unitDtoFavorite.setFavorite(true);
            }
        }

        return units.stream()
                .map(unitFavoriteMapper::toUnitFavoriteDto)
                .collect(Collectors.toList());
    }

    public Page<UnitDtoFavorite> getUnitsByAccommodationTypeName(Long accommodationTypeId, int page, int size, Sort sort) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Unit> unitsPage = unitRepository.findByAccommodationType_Id(accommodationTypeId, pageRequest);

        return unitsPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    public Page<UnitDashboard> getUnitsByAccommodationTypeNameDashboard(Long accommodationTypeId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Unit> unitsPage = unitRepository.findByAccommodationType_Id(accommodationTypeId, pageRequest);

        return unitsPage.map(unitDashboardMapper::toUnitDashboard);
    }

    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }

    public List<Unit> getAllUnitForMap() {
        return unitRepository.findAll();
    }


    public Page<Unit> getUnitsForUserAndStatus(Long userId, Long statusUnitId, Pageable pageable) {
        return unitRepository.findAllByUserIdAndStatusUnitId(userId, statusUnitId, pageable);
    }

    public Page<UnitDtoFavorite> getUnitsByUserCity(Long userId, Pageable pageable, Sort sort) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null || user.getCity() == null) {
            return Page.empty(); // Return an empty page if user or user's city is not found
        }

        City userCity = user.getCity();
        Page<Unit> unitPage = unitRepository.findByCity(userCity, pageable);

        return unitPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    public Page<Unit> getUnitsByUnitTypeId(Long unitTypeId, Pageable pageable) {
        return unitRepository.findByUnitType_Id(unitTypeId, pageable);
    }

    public List<Unit> getUnitTypeIdForMap(Long unitTypeId) {
        return unitRepository.findByUnitType_IdForMap(unitTypeId);
    }

    public List<Unit> getUnitsByUserIdList(Long userId ) {
        return unitRepository.findByUserIdList(userId);
    }

    public Page<Unit> getUnitsByUserId(Long userId, Pageable pageable ) {
        return unitRepository.findByUserId(userId, pageable);
    }


    public Page<Unit> filterUnitsByName(String nameUnit, Pageable pageable) {
        return unitRepository.findByNameUnitContainingIgnoreCase(nameUnit, pageable);
    }

    public List<Unit> filterUnitsByNameAndTypeIdForMap(String nameUnit, Long unitTypeId) {
        return unitRepository.findByNameUnitAndUnitTypeForMap(nameUnit, unitTypeId);
    }
    public Page<Unit> filterUnitsByUserNameAndTypeId(String userName, Long unitTypeId, Pageable pageable) {
        return unitRepository.findByUsernameAndUnitType(userName, unitTypeId, pageable);
    }

    public Page<Unit> filterUnitsByPhoneNumberAndTypeId(String phoneNumber, Long unitTypeId, Pageable pageable) {
        return unitRepository.findByPhoneNumberAndUnitType(phoneNumber, unitTypeId, pageable);
    }

    public List<Unit> filterUnitsByNameForMap(String nameUnit) {
        return unitRepository.findByNameUnitContainingIgnoreCaseForMap(nameUnit);
    }
    public Page<Unit> filterUnitsByRoomAvailableName(String roomAvailableName, Pageable pageable) {
        return unitRepository.findByRoomAvailableName(roomAvailableName, pageable);
    }

    public Page<Unit> findByNameUnitAndRoomAvailableNameContainingIgnoreCase(String nameUnit, String roomAvailableName, Pageable pageable) {
        return unitRepository.findByNameUnitAndRoomAvailableNameContainingIgnoreCase(nameUnit, roomAvailableName, pageable);
    }

    public Page<Unit> filterUnitsByAvailableAreaName(String availableAreaName, Pageable pageable) {
        return unitRepository.findByAvailableAreaName(availableAreaName, pageable);
    }
    public Page<Unit> findByNameUnitAndAvailableAreaNameContainingIgnoreCase(String nameUnit, String availableAreaName, Pageable pageable) {
        return unitRepository.findByNameUnitAndAvailableAreaNameContainingIgnoreCase(nameUnit, availableAreaName, pageable);
    }

    public void updateStatusForUser(Long unitId, Long statusUnitId) throws IOException, InterruptedException {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with id: " + unitId));

        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));

        unit.setStatusUnit(statusUnit);

        unitRepository.save(unit);

        if ( statusUnitId == 2) {
            System.out.println("statusUnitId");
            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_accepted_units.message", null, LocaleContextHolder.getLocale()) + " " + unit.getNameUnit(), unit.getUser().getId(), unit.getId(), null, null);
            notificationService.processNotification(notificationRequest);
        } else if ( statusUnitId == 3) {
            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_rejected_units.message", null, LocaleContextHolder.getLocale()) + " " + unit.getNameUnit(), unit.getUser().getId(), unit.getId(), null, null);
            notificationService.processNotification(notificationRequest);
        }
    }

    public void calculateAndSetAverageEvaluation(Long unitId) {
        List<Reservations> reservations = reservationsRepository.findByUnitId(unitId);
        double totalScore = 0.0;
        int count = 0;
        for (Reservations reservation : reservations) {
            if (reservation.getEvaluation() != null) {
                totalScore += reservation.getEvaluation().getScore(); // Assuming getScore() returns the evaluation score
                count++;
                System.out.println("I'm in for loop");
            }
        }
        if (count > 0) {
            double averageScore = totalScore / count;

            Long evaluationId;
            if (averageScore >= 9) {
                evaluationId = 1L; // Excellent: 9+
            } else if (averageScore >= 8) {
                evaluationId = 2L; // Very Good: 8+
            } else if (averageScore >= 7) {
                evaluationId = 3L; // Good: 7+
            } else if (averageScore >= 6) {
                evaluationId = 4L; // Acceptable: 6+
            } else {
                evaluationId = null; // Handle the case when averageScore is out of range
            }

            System.out.println("averageScore: " + averageScore);
            // Get the unit
            Unit unit = unitRepository.findById(unitId)
                    .orElseThrow(() -> new RuntimeException("Unit not found"));

            Evaluation evaluation = evaluationRepository.findById(evaluationId)
                    .orElseThrow(() -> new RuntimeException("evaluation not found"));

            System.out.println("Evaluation: " + evaluation.getId());


            unit.setEvaluation(evaluation);

            unit.incrementTotalEvaluation();

            unitRepository.save(unit);
        }
    }

    public void updateEvaluationsForUnits(Long unitId) {
        calculateAndSetAverageEvaluation(unitId);
    }

    @Transactional
    public void deleteUnitWithDependencies(Long id) {

        List<ReserveDate> reserveDateList = reserveDateRepository.findListByUnitId(id);
        for (ReserveDate reserveDate: reserveDateList) {
            reserveDateRepository.deleteDateInfoByReserveDateId(reserveDate.getId());
        }

        List<ReserveDateHalls> reserveDateHallsList = reserveDateHallsService.getByUnitId(id);
        for (ReserveDateHalls reserveDateHalls: reserveDateHallsList) {
            reserveDateHallsService.deleteDateInfoHallsByReserveDateHallsId(reserveDateHalls.getId());
        }

        List<ReserveDateRoomDetails> reserveDateRoomDetailsList = reserveDateRoomDetailsRepository.findListByUnitId(id);
        for (ReserveDateRoomDetails reserveDateRoomDetails : reserveDateRoomDetailsList) {
            reserveDateRoomDetailsRepository.deleteDateInfoByReserveDateId(reserveDateRoomDetails.getId());
        }

        List<ReserveDateUnit> reserveDateUnitList = reserveDateUnitService.getListByUnitId(id);
        for (ReserveDateUnit reserveDateUnit : reserveDateUnitList) {
            reserveDateUnitService.deleteDateInfoByReserveDateId(reserveDateUnit.getId());
        }


        reserveDateHallsService.deleteByUnitId(id);
        reserveDateRepository.deleteByUnitId(id);
        reserveDateRoomDetailsRepository.deleteByUnitId(id);
        reserveDateUnitService.deleteByUnitId(id);

        userFavoriteUnitRepository.deleteByUnit(id);


        roomDetailsForAvailableAreaRepository.deleteByUnitId(id);
        roomDetailsRepository.deleteByUnitId(id);


        fileForUnitRepository.deleteByUnitId(id);
        fileForAdsRepository.deleteByUnitId(id);
        reservationRepository.deleteByUnitId(id);

        technicalSupportUnitRepository.deleteByUnitId(id);

        Ads ads = adsRepository.findByUnitId(id);

        if (ads != null) {
            adsService.deleteAds(ads.getId());
        }
        deleteUnit(id);
    }


    public Page<Unit> getUnitsByUnitTypeIdForDashboard(Long unitTypeId, Pageable pageable) {
        return unitRepository.findByUnitType_IdForDashboard(unitTypeId, pageable);
    }

    public void setCommissionForAllUnits(Double commission) {
        List<Unit> units = unitRepository.findAll();
        for (Unit unit : units) {
            unit.setCommission(commission);
        }
        unitRepository.saveAll(units);
    }

    public CounterUnits getCounterForResidenciesUnits() {
        CounterUnits counterUnits = new CounterUnits();

        counterUnits.setCounterAllResidencies(unitRepository.countByAccommodationTypeIdNull());
        counterUnits.setCounterAllHotel(unitRepository.countByAccommodationTypeIdOne());
        counterUnits.setCounterAllHotelPartment(unitRepository.countByAccommodationTypeIdTwo());
        counterUnits.setCounterAllExternalPartment(unitRepository.countByAccommodationTypeIdThree());
        counterUnits.setCounterResort(unitRepository.countByAccommodationTypeIdFour());
        counterUnits.setCounterChalet(unitRepository.countByAccommodationTypeIdFive());
        counterUnits.setCounterlounge(unitRepository.countByAccommodationTypeIdSix());

        return counterUnits;
    }

    public Page<Unit> getFiltering(Long cityId, Long regionId, Long unitTypeId,
                                   Long typesOfEventHallsIds, Long availablePeriodsHallsSetId,
                                   Set<Long> accommodationTypeIds, Set<Long> hotelClassificationIds,
                                   Set<Long> basicFeaturesSetIds, Set<Long> featuresHallsSetIds,
                                   Set<Long> subFeaturesSetIds, Set<Long> evaluationIds,
                                   Integer minCapacityHalls, Integer maxCapacityHalls,
                                   Integer minAdultsAllowed, Integer maxAdultsAllowed,
                                   Integer minChildrenAllowed, Integer maxChildrenAllowed,
                                   Integer priceMin, Integer priceMax,
                                   LocalDate dateOfArrival, LocalDate departureDate,
                                   Pageable pageable) {


        Page<Unit> unitContent = unitRepository.findFilteringTwo(cityId, regionId, unitTypeId, typesOfEventHallsIds,
                availablePeriodsHallsSetId, accommodationTypeIds, hotelClassificationIds,
                basicFeaturesSetIds, featuresHallsSetIds, subFeaturesSetIds,
                evaluationIds,
                minCapacityHalls, maxCapacityHalls,
                minAdultsAllowed, maxAdultsAllowed,
                minChildrenAllowed, maxChildrenAllowed,
                priceMin, priceMax,
                dateOfArrival, departureDate, pageable);


        for (Unit unit : unitContent.getContent()) {
            System.out.println("unitContent: " + unit.getId());
        }

        for (Unit unit: unitContent.getContent()) {
            System.out.println("Unit Id: " + unit.getId());
        }

        return unitContent;
    }

    public Page<Unit> filterUnitsByNameAndTypeId(String nameUnit, Long unitTypeId, Pageable pageable) {
        return unitRepository.findByNameUnitAndUnitType(nameUnit, unitTypeId, pageable);
    }

    public Page<Unit> getAllUnit(Pageable pageable) {
        return unitRepository.findAllByStatusUnitId(pageable);
    }

//    public List<Unit> findUnitsByFilters(Long cityId, Long regionId, Long availablePeriodsId,
//                                         Long unitTypeId, Long hallTypeId, Set<Long> accommodationTypeIds, Set<Long> hotelClassificationIds,
//                                         Set<Long> basicFeaturesIds, Set<Long> featuresHallsIds, Set<Long> subFeaturesIds, Set<Long> foodOptionsIds,
//                                         Set<Long> evaluationIds, int capacityHalls, int adultsAllowed, int childrenAllowed, int priceMin, int priceMax
//            , LocalDate dateOfArrival, LocalDate departureDate, Sort sort) {
//        Specification<Unit> spec = Specification.where(null);
//
//        if (cityId != null) {
//            spec = spec.and(UnitSpecifications.byCity(cityId));
//        }
//
//        if (regionId != null) {
//            spec = spec.and(UnitSpecifications.byRegion(regionId));
//        }
//
//        if (availablePeriodsId != null) {
//            spec = spec.and(UnitSpecifications.byAvailablePeriod(availablePeriodsId));
//        }
//
//
//        if (unitTypeId != null) {
//            spec = spec.and(UnitSpecifications.byUnitTypeId(unitTypeId));
//        }
//
//        if (hallTypeId != null) {
//            spec = spec.and(UnitSpecifications.byHallTypeId(hallTypeId));
//        }
//
//        if (accommodationTypeIds != null) {
//            spec = spec.and(UnitSpecifications.byAccommodationTypeIds(accommodationTypeIds));
//        }
//
//        if (hotelClassificationIds != null) {
//            spec = spec.and(UnitSpecifications.byHotelClassificationIds(hotelClassificationIds));
//        }
//
//        if (evaluationIds != null) {
//            spec = spec.and(UnitSpecifications.byEvaluationIds(evaluationIds));
//        }
//
//        if (basicFeaturesIds != null && !basicFeaturesIds.isEmpty()) {
//            spec = spec.and(UnitSpecifications.byBasicFeaturesIds(basicFeaturesIds));
//        }
//
//        if (featuresHallsIds != null && !featuresHallsIds.isEmpty()) {
//            spec = spec.and(UnitSpecifications.byFeaturesHallsIds(featuresHallsIds));
//        }
//
//        if (subFeaturesIds != null && !subFeaturesIds.isEmpty()) {
//            spec = spec.and(UnitSpecifications.bySubFeaturesIds(subFeaturesIds));
//        }
//
//        if (capacityHalls != 0) {
//            spec = spec.and(UnitSpecifications.byCapacityHalls(capacityHalls));
//        }
//
//        if (adultsAllowed != 0) {
//            spec = spec.and(UnitSpecifications.byAdultsAllowed(adultsAllowed));
//        }
//        if (childrenAllowed != 0) {
//            spec = spec.and(UnitSpecifications.byChildrenAllowed(childrenAllowed));
//        }
//
//        if (priceMin > 0) {
//            spec = spec.and((root, query, criteriaBuilder) ->
//                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceMin));
//        }
//
//        if (priceMax > 0) {
//            spec = spec.and((root, query, criteriaBuilder) ->
//                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceMax));
//        }
//
//        if (dateOfArrival != null) {
//            spec = spec.and((root, query, criteriaBuilder) ->
//                    criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfArrival"), dateOfArrival));
//        }
//
//        if (departureDate != null) {
//            spec = spec.and((root, query, criteriaBuilder) ->
//                    criteriaBuilder.lessThanOrEqualTo(root.get("departureDate"), departureDate));
//        }
//
//        spec = spec.and((root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get("statusUnit").get("id"), 2));
//
//        return unitRepository.findAll(spec, sort);
//    }

    @Transactional
    public UnitDto updateYoutubeUrl(Long unitId, String youtubeUrl) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with id: " + unitId));
        unit.setYoutubeUrl(youtubeUrl);
        Unit updatedUnit = unitRepository.save(unit);
        return unitMapper.toUnitDto(updatedUnit);
    }

    public List<Unit> findUnitsByAvailability(LocalDate dateOfArrival, LocalDate departureDate, Boolean isMorning, Boolean isEvening) {
        return unitRepository.findUnitsByAvailability(dateOfArrival, departureDate, isMorning, isEvening);
    }
}
