package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.TotalTransactions;
import com.AlTaraf.Booking.database.entity.TransactionsDetail;
import com.AlTaraf.Booking.database.repository.TotalTransactionsRepository;
import com.AlTaraf.Booking.database.repository.TransactionsDetailRepository;
import com.AlTaraf.Booking.rest.dto.TransactionDetailsDto;
import com.AlTaraf.Booking.rest.mapper.TransactionDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionsDetailRepository transactionsDetailRepository;

    @Autowired
    TransactionDetailsMapper transactionDetailsMapper;

    @Autowired
    TotalTransactionsRepository totalTransactionsRepository;

    public List<TotalTransactions> getAllTotalTransactions() {
        return totalTransactionsRepository.findAll();
    }

    public Page<TransactionDetailsDto> getAllTransactionDetails(Pageable pageable) {
        Page<TransactionsDetail> transactionsDetailPage = transactionsDetailRepository.findAll(pageable);
        return transactionsDetailPage.map(TransactionDetailsMapper.INSTANCE::toDto);
    }

    public Page<TransactionDetailsDto> findByPhone(String phone, Pageable pageable) {
        Page<TransactionsDetail> transactionsDetailPage = transactionsDetailRepository.findByPhone(phone, pageable);
        return transactionsDetailPage.map(TransactionDetailsMapper.INSTANCE::toDto);
    }

    public Page<TransactionDetailsDto> findByTransactionId(Long transactionId, Pageable pageable) {
        Page<TransactionsDetail> transactionsDetailPage = transactionsDetailRepository.findByTransactionsId(transactionId, pageable);
        return transactionsDetailPage.map(TransactionDetailsMapper.INSTANCE::toDto);
    }

    public Page<TransactionDetailsDto> findByTransactionIdAndPhone(Long transactionId, String phone, Pageable pageable) {
        Page<TransactionsDetail> transactionsDetailPage = transactionsDetailRepository.findByTransactionsIdAndPhone(transactionId, phone, pageable);
        return transactionsDetailPage.map(TransactionDetailsMapper.INSTANCE::toDto);
    }

}
