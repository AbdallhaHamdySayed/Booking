package com.AlTaraf.Booking.Service.unit;

import com.AlTaraf.Booking.Dto.Unit.UnitDashboard;
import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Payload.response.CounterUnits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface UnitService {

    Unit saveUnit( Unit unit);

    Page<UnitDtoFavorite> getUnitByEvaluationInOrderByEvaluationScoreDesc(Long userId, int page, int size);
    Unit getUnitById(Long id);

    List<UnitDtoFavorite> getNewlyAdded(Long userId);

    Page<UnitDtoFavorite> getUnitsByAccommodationTypeName(Long accommodationTypeId, int page, int size, Sort sort);
    Page<UnitDashboard> getUnitsByAccommodationTypeNameDashboard(Long accommodationTypeId, int page, int size);

    void deleteUnit(Long id);

    List<Unit> getAllUnitForMap();
    Page<Unit> getAllUnit(Pageable pageable);

    Page<Unit> getUnitsForUserAndStatus(Long userId, Long statusUnitId, Pageable pageable);
    Page<UnitDtoFavorite> getUnitsByUserCity(Long userId, Pageable pageable, Sort sort);

    Page<Unit> getUnitsByUnitTypeId(Long unitTypeId, Pageable pageable);
    List<Unit> getUnitTypeIdForMap(Long unitTypeId);

    List<Unit> getUnitsByUserIdList(Long userId );

    Page<Unit> getUnitsByUserId(Long userId, Pageable pageable );

    List<Unit> findUnitsByFilters(Long cityId, Long regionId, Long availablePeriodsId,
                                  Long unitTypeId, Long hallTypeId, Set<Long> accommodationTypeIds, Set<Long> hotelClassificationIds,
                                  Set<Long> basicFeaturesIds, Set<Long> featuresHallsIds, Set<Long> subFeaturesIds, Set<Long> foodOptionsIds,
                                  Set<Long> evaluationId,
                                  int capacityHalls, int adultsAllowed, int childrenAllowed, int priceMin, int priceMax
            , LocalDate dateOfArrival, LocalDate departureDate, Sort sort);

    Page<Unit> filterUnitsByName(String nameUnit, Pageable pageable);


    Page<Unit> filterUnitsByNameAndTypeId(String nameUnit, Long unitTypeId, Pageable pageable);

    Page<Unit> filterUnitsByUserNameAndTypeId(String userName, Long unitTypeId, Pageable pageable);

    Page<Unit> filterUnitsByPhoneNumberAndTypeId(String phoneNumber, Long unitTypeId, Pageable pageable);

    List<Unit> filterUnitsByNameForMap(String nameUnit);

    List<Unit> filterUnitsByNameAndTypeIdForMap(String nameUnit, Long unitTypeId);

    Page<Unit> filterUnitsByRoomAvailableName(String roomAvailableName, Pageable pageable);
    Page<Unit> findByNameUnitAndRoomAvailableNameContainingIgnoreCase(String nameUnit, String roomAvailableName, Pageable pageable);

    Page<Unit> filterUnitsByAvailableAreaName(String availableAreaName, Pageable pageable);

    Page<Unit> findByNameUnitAndAvailableAreaNameContainingIgnoreCase(String nameUnit, String availableAreaName, Pageable pageable);

    void updateStatusForUser(Long userId, Long statusUnitId) throws IOException, InterruptedException;

    void calculateAndSetAverageEvaluation(Long unitId);

    void updateEvaluationsForUnits(Long unitId);

    @Transactional
    void deleteUnitWithDependencies(Long id);

    Page<Unit> getUnitsByUnitTypeIdForDashboard(Long unitTypeId, Pageable pageable);

    void setCommissionForAllUnits(Double commission);

    CounterUnits getCounterForResidenciesUnits();

    Page<Unit> getFiltering(Long cityId, Long regionId, Long unitTypeId, Long typesOfEventHallsIds,
                             Long availablePeriodsHallsSetId, Set<Long> accommodationTypeIds, Set<Long> hotelClassificationIds,
                            Set<Long> basicFeaturesSetIds, Set<Long> featuresHallsSetIds, Set<Long> subFeaturesSetIds,
                            Set<Long> evaluationIds,
                            Integer minCapacityHalls, Integer maxCapacityHalls,
                            Integer minAdultsAllowed, Integer maxAdultsAllowed,
                            Integer minChildrenAllowed, Integer maxChildrenAllowed,
                            Integer priceMin, Integer priceMax,
                            LocalDate dateOfArrival, LocalDate departureDate, Pageable pageable);
}
