package com.AlTaraf.Booking.Service.Transaction;

import com.AlTaraf.Booking.Dto.Transactions.TransactionDetailsDto;
import com.AlTaraf.Booking.Entity.Transactions.TotalTransactions;
import com.AlTaraf.Booking.Entity.Transactions.TransactionsDetail;
import com.AlTaraf.Booking.Mapper.Transactions.TransactionDetailsMapper;
import com.AlTaraf.Booking.Repository.Transactions.TotalTransactionsRepository;
import com.AlTaraf.Booking.Repository.Transactions.TransactionsDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionsDetailRepository transactionsDetailRepository;

    @Autowired
    TransactionDetailsMapper transactionDetailsMapper;

    @Autowired
    private TotalTransactionsRepository totalTransactionsRepository;

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
