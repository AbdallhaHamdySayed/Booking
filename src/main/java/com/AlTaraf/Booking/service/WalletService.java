package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.Wallet;
import com.AlTaraf.Booking.database.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class WalletService  {

    private WalletRepository walletRepository;

    public List<Wallet> getWalletsByUserId(Long userId , Pageable pageable) {
        return walletRepository.findByUserId(userId, pageable);
    }
}
