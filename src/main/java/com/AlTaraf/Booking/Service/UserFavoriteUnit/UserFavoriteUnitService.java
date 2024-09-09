package com.AlTaraf.Booking.Service.UserFavoriteUnit;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserFavoriteUnitService {

    void saveUserFavoriteUnit(User user, Unit unit);

    Page<UnitDtoFavorite> getUserFavoriteUnitsByUserId(Long userId, Pageable pageable);

    void deleteUserFavoriteUnit(Long userId, Long unitId);

    boolean existsByUserAndUnit(User user, Unit unit);

    boolean existsByUserIdAndUnitId(Long userId, Long unitId);

}
