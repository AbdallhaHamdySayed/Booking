package com.AlTaraf.Booking.Service.UserFavoriteUnit;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Favorite.UserFavoriteUnit;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.Repository.UserFavoriteUnit.UserFavoriteUnitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserFavoriteUnitServiceImpl implements UserFavoriteUnitService {

    @Autowired
    UserFavoriteUnitRepository userFavoriteUnitRepository;

    @Autowired
    UnitFavoriteMapper unitFavoriteMapper;

    @Override
    public List<UserFavoriteUnit> findByUser(User user) {
        return userFavoriteUnitRepository.findByUser(user);
    }

    @Override
    public void saveUserFavoriteUnit(User user, Unit unit) {
        UserFavoriteUnit userFavoriteUnit = new UserFavoriteUnit();
        userFavoriteUnit.setUser(user);
        userFavoriteUnit.setUnit(unit);
        userFavoriteUnit.setFavorite(true);
        userFavoriteUnitRepository.save(userFavoriteUnit);
    }

    public Page<UnitDtoFavorite> getUserFavoriteUnitsByUserId(Long userId, Pageable pageable) {
        Page<UserFavoriteUnit> userFavoriteUnitsPage = userFavoriteUnitRepository.findByUserId(userId, pageable);
        List<UnitDtoFavorite> unitDtoFavorites = unitFavoriteMapper.toUnitFavoriteDtoList(userFavoriteUnitsPage.stream()
                .map(UserFavoriteUnit::getUnit)
                .collect(Collectors.toList()));
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
}
