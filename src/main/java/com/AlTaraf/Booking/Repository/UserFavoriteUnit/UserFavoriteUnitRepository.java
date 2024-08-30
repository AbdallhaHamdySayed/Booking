package com.AlTaraf.Booking.Repository.UserFavoriteUnit;

import com.AlTaraf.Booking.Entity.Favorite.UserFavoriteUnit;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFavoriteUnitRepository extends JpaRepository<UserFavoriteUnit, Long> {
    List<UserFavoriteUnit> findByUser(User user);

    @Query("SELECT uf FROM UserFavoriteUnit uf WHERE uf.user.id = :userId")
    Page<UserFavoriteUnit> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT COUNT(u) > 0 FROM UserFavoriteUnit u WHERE u.user = :user AND u.unit = :unit")
    boolean existsByUserAndUnit(@Param("user") User user, @Param("unit") Unit unit);

    @Modifying
    @Query("DELETE FROM UserFavoriteUnit uf WHERE uf.user = :user AND uf.unit = :unit")
    void deleteByUserAndUnit(@Param("user") User user, @Param("unit") Unit unit);

    @Query("SELECT uf FROM UserFavoriteUnit uf WHERE uf.user.id = :userId AND uf.unit.id = :unitId")
    Optional<UserFavoriteUnit> findByUserAndUnit(@Param("userId") Long userId, @Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserFavoriteUnit uf WHERE uf.unit.id = :unitId")
    void deleteByUnit(@Param("unitId") Long unitId);

    void deleteByUser(User user);

}