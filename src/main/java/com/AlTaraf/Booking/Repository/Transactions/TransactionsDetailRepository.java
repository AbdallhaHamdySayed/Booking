package com.AlTaraf.Booking.Repository.Transactions;

import com.AlTaraf.Booking.Entity.Transactions.TransactionsDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionsDetailRepository extends JpaRepository<TransactionsDetail, Long> {
    @Query("SELECT td FROM TransactionsDetail td WHERE td.user.id = :userId")
    Page<TransactionsDetail> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT td FROM TransactionsDetail td WHERE td.phone = :phone")
    Page<TransactionsDetail> findByPhone(@Param("phone") String phone, Pageable pageable);

    @Query("SELECT td FROM TransactionsDetail td WHERE td.transactions.id = :transactionId")
    Page<TransactionsDetail> findByTransactionsId(@Param("transactionId") Long transactionId, Pageable pageable);

    @Query("SELECT td FROM TransactionsDetail td WHERE td.transactions.id = :transactionId AND td.phone = :phone")
    Page<TransactionsDetail> findByTransactionsIdAndPhone(@Param("transactionId") Long transactionId, @Param("phone") String phone, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM TransactionsDetail td WHERE td.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
