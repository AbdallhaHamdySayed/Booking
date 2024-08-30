package com.AlTaraf.Booking.Controller.Wallet;


import com.AlTaraf.Booking.Entity.Wallet.Wallet;
import com.AlTaraf.Booking.Service.Wallet.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wallet>> getWalletsByUserId(@PathVariable Long userId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        List<Wallet> wallets = walletService.getWalletsByUserId(userId, pageable);
        return ResponseEntity.ok(wallets);
    }
}
