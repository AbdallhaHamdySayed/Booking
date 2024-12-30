package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.Unit;
import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.database.entity.UserFavoriteUnit;
import com.AlTaraf.Booking.database.repository.UserFavoriteUnitRepository;
import com.AlTaraf.Booking.rest.dto.UnitDtoFavorite;
import com.AlTaraf.Booking.rest.mapper.UnitFavoriteMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserFavoriteUnitService {

    @Autowired
    UserFavoriteUnitRepository userFavoriteUnitRepository;

    @Autowired
    UnitFavoriteMapper unitFavoriteMapper;

    public void saveUserFavoriteUnit(User user, Unit unit) {
        UserFavoriteUnit userFavoriteUnit = new UserFavoriteUnit();
        userFavoriteUnit.setUser(user);
        userFavoriteUnit.setUnit(unit);
        userFavoriteUnit.setFavorite(true);
        userFavoriteUnitRepository.save(userFavoriteUnit);
    }

    public Page<UnitDtoFavorite> getUserFavoriteUnitsByUserId(Long userId, Pageable pageable) {
        Page<UserFavoriteUnit> userFavoriteUnitsPage = userFavoriteUnitRepository.findByUserId(userId, pageable);

        List<Long> unitIds = userFavoriteUnitsPage.stream()
                .map(userFavoriteUnit -> userFavoriteUnit.getUnit().getId())
                .collect(Collectors.toList());

        Map<Long, Boolean> unitFavoriteStatusMap = unitIds.stream()
                .collect(Collectors.toMap(
                        unitId -> unitId,
                        unitId -> userFavoriteUnitRepository.existsByUserIdAndUnitId(userId, unitId)
                ));

        userFavoriteUnitsPage.forEach(userFavoriteUnit -> {
            Unit unit = userFavoriteUnit.getUnit();
            unit.setFavorite(unitFavoriteStatusMap.getOrDefault(unit.getId(), false));
        });

        // Map to UnitDtoFavorite
        List<UnitDtoFavorite> unitDtoFavorites = unitFavoriteMapper.toUnitFavoriteDtoList(
                userFavoriteUnitsPage.stream()
                        .map(UserFavoriteUnit::getUnit)
                        .collect(Collectors.toList())
        );

        return new PageImpl<>(unitDtoFavorites, pageable, userFavoriteUnitsPage.getTotalElements());
    }
    public void deleteUserFavoriteUnit(Long userId, Long unitId) {
        Optional<UserFavoriteUnit> userFavoriteUnitOptional = userFavoriteUnitRepository.findByUserAndUnit(userId, unitId);
        UserFavoriteUnit userFavoriteUnit = userFavoriteUnitOptional.orElseThrow(() -> new EntityNotFoundException("UserFavoriteUnit not found for user: " + userId + " and unit: " + unitId));

        userFavoriteUnitRepository.delete(userFavoriteUnit);
    }
    public boolean existsByUserAndUnit(User user, Unit unit) {
        return userFavoriteUnitRepository.existsByUserAndUnit(user, unit);
    }
    public boolean existsByUserIdAndUnitId(Long userId, Long unitId) {
        return userFavoriteUnitRepository.existsByUserIdAndUnitId(userId, unitId);
    }

}
