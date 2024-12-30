package com.AlTaraf.Booking.database.repository;


import com.AlTaraf.Booking.database.entity.Notifications;
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

    Page<Notifications> findByUserId(Long userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Notifications n WHERE n.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    @Query("SELECT n FROM Notifications n JOIN n.user u JOIN n.role r WHERE u.id = :userId AND r.id = :roleId ORDER BY CASE WHEN n.seen IS NULL THEN 0 ELSE 1 END ASC, n.createdDate DESC")
    Page<Notifications> findAllByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId, Pageable pageable);

    @Query("SELECT COUNT(n) FROM Notifications n WHERE n.user.id = :userId AND n.role.id = :roleId AND n.seen IS NULL")
    Long countByUserIdAndRoleIdAndSeenIsNull(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
