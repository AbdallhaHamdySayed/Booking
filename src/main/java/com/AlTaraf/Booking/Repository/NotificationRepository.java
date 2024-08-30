package com.AlTaraf.Booking.Repository;

import com.AlTaraf.Booking.Entity.Notifications.Notifications;
import com.AlTaraf.Booking.Entity.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {

    @Query("SELECT n FROM Notifications n WHERE n.user.id = :userId AND n.role.id IS NULL")
    Page<Notifications> findByUserIdAndRoleIdIsNull(@Param("userId") Long roleId, Pageable pageable);

    @Query("SELECT n FROM Notifications n WHERE n.role.id = :roleId AND n.user.id IS NULL")
    Page<Notifications> findByRoleIdAndUserIdIsNull(@Param("roleId") Long roleId, Pageable pageable);

    Page<Notifications> findByUserId(Long userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Notifications n WHERE n.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

//    @Query("SELECT n FROM Notifications n JOIN n.user u JOIN n.role r WHERE u.id = :userId AND r.id = :roleId")
@Query("SELECT n FROM Notifications n JOIN n.user u JOIN n.role r WHERE u.id = :userId AND r.id = :roleId ORDER BY CASE WHEN n.seen IS NULL THEN 0 ELSE 1 END ASC, n.createdDate DESC")
Page<Notifications> findAllByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId, Pageable pageable);


//    @Query("SELECT n FROM Notifications n JOIN n.user u JOIN n.role r WHERE (:userId IS NULL OR u.id = :userId) AND (:roleId IS NULL OR r.id = :roleId)")
//    Page<Notifications> findAllByUserIdAndRoleIdOrUserIdIsNull(@Param("userId") Long userId, @Param("roleId") Long roleId, Pageable pageable);

    @Query("SELECT n FROM Notifications n WHERE n.user.id IS NULL AND n.role.id = :roleId")
    Page<Notifications> findAllByRoleId(@Param("roleId") Long roleId, Pageable pageable);

    @Query("SELECT n FROM Notifications n WHERE n.user.id IS NULL AND n.role.id IS NULL")
    Page<Notifications> findAllByRoleIdIsNullAndUserIdIsNull(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Notifications n SET n.seen = true WHERE n.user.id = :userId AND n.role.id = :roleId")
    void setNotificationsSeenByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Query("SELECT COUNT(n) FROM Notifications n WHERE n.user.id = :userId AND n.role.id = :roleId AND n.seen IS NULL")
    Long countByUserIdAndRoleIdAndSeenIsNull(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
