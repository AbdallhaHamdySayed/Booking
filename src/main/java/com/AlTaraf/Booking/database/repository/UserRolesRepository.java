package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.Role;
import com.AlTaraf.Booking.database.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {

    @Query("SELECT ur.role FROM UserRoles ur WHERE ur.user.id = :userId")
    List<Role> findRoleByUserId(@Param("userId") Long userId);

}
