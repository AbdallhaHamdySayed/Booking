package com.AlTaraf.Booking.Service.UserFavoriteUnit;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Favorite.UserFavoriteUnit;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserFavoriteUnitService {
    List<UserFavoriteUnit> findByUser(User user);

    void saveUserFavoriteUnit(User user, Unit unit);

    Page<UnitDtoFavorite> getUserFavoriteUnitsByUserId(Long userId, Pageable pageable);

    void deleteUserFavoriteUnit(Long userId, Long unitId);

    boolean existsByUserAndUnit(User user, Unit unit);
}
