package com.AlTaraf.Booking.Controller.Transactions;

import com.AlTaraf.Booking.Dto.Transactions.TransactionDetailsDto;
import com.AlTaraf.Booking.Entity.Transactions.TotalTransactions;
import com.AlTaraf.Booking.Service.Transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/details")
    public ResponseEntity<?> getAllTransactionDetails(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Long transactionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<TransactionDetailsDto> transactionDetailsDtoPage;
        if (transactionId != null && phone != null){
            transactionDetailsDtoPage = transactionService.findByTransactionIdAndPhone(transactionId, phone, pageable);
        }
        else if (phone != null && transactionId == null) {
            transactionDetailsDtoPage = transactionService.findByPhone(phone, pageable);
        } else if (transactionId != null && phone == null) {
            transactionDetailsDtoPage = transactionService.findByTransactionId(transactionId, pageable);
        } else {
            transactionDetailsDtoPage = transactionService.getAllTransactionDetails(pageable);
        }

        return ResponseEntity.ok(transactionDetailsDtoPage);
    }

    @GetMapping("/total")
    public ResponseEntity<List<TotalTransactions>> getAllTotalTransactions() {
        List<TotalTransactions> totalTransactions = transactionService.getAllTotalTransactions();
        return ResponseEntity.ok(totalTransactions);
    }

}
