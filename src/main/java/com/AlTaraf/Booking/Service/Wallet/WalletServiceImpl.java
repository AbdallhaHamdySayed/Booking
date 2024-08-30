package com.AlTaraf.Booking.Service.Wallet;

import com.AlTaraf.Booking.Entity.Wallet.Wallet;
import com.AlTaraf.Booking.Repository.Wallet.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    public List<Wallet> getWalletsByUserId(Long userId, Pageable pageable) {
        return walletRepository.findByUserId(userId, pageable);
    }
}
