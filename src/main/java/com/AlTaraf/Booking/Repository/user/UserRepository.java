package com.AlTaraf.Booking.Repository.user;

import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findByUserId(@Param("userId") Long userId);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.id = :roleId")
    List<User> findByRoleId(@Param("roleId") Long roleId);

    Optional<User> findByPhone(String phone);

    @Query("SELECT u FROM User u WHERE u.phone = :phone")
    User findByPhoneForUser(@Param("phone") String phone);

    @Query("SELECT COUNT(u.id) > 0 FROM User u " +
            "JOIN u.roles r " +
            "WHERE (u.phone = :phone) " +
            "AND r.name IN :roleNames "+
            "AND u.isActive = true")
    boolean existsByEmailAndPhoneNumberAndRoles(
//            @Param("email") String email,
            @Param("phone") String phone,
            @Param("roleNames") Set<ERole> roleNames);

    Page<User> findAllByRolesName(ERole roleName, Pageable pageable);

    Page<User> findAllByRolesNameAndUsernameAndPhone(
            ERole roleName, String username, String phone, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.username = :username AND r.name NOT IN ('ROLE_ADMIN', 'ROLE_SERVICE')")
    Page<User> findByUsername(String username, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.username = :username AND r.name NOT IN ('ROLE_GUEST', 'ROLE_LESSOR')")
    Page<User> findByUsernameDashboard(String username, Pageable pageable);

    Page<User> findByUsernameAndRolesName(String username, ERole roleName, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.phone = :phone AND r.name NOT IN ('ROLE_ADMIN', 'ROLE_SERVICE')")
    Page<User> findAllByPhoneExcludingRoles(@Param("phone") String phone, Pageable pageable);


    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.phone = :phone AND r.name NOT IN ('ROLE_GUEST', 'ROLE_LESSOR')")
    Page<User> findAllByPhoneExcludingRolesDashboard(@Param("phone") String phone, Pageable pageable);

    Page<User> findAllByPhoneAndRolesName(String phone, ERole roleName, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName AND u.id = :userId")
    User findByRolesNameAndUserId(@Param("roleName") ERole roleName, @Param("userId") Long userId);

    @Query("SELECT COUNT(u) FROM User u")
    Long countAllUsers();

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.id = 1")
    Long countUsersByRoleIdOne();

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.id = 2")
    Long countUsersByRoleIdTwo();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findAllByRoles_Name(@Param("roleName") ERole roleName);

    @Query("SELECT u FROM User u WHERE u.isActive IS NULL")
    List<User> findAllUserIsNotActive();

    @Modifying
    @Query("UPDATE User u SET u.isActive = true WHERE u.id = :userId")
    void activateUserById(@Param("userId") Long userId);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name NOT IN ('ROLE_ADMIN', 'ROLE_SERVICE')")
    Page<User> findAllExclude( Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name NOT IN ('ROLE_GUEST', 'ROLE_LESSOR')")
    Page<User> findAllExcludeDashboard( Pageable pageable);


}

