package edu.nikitazubov.walletservice.controller;

import edu.nikitazubov.walletservice.dto.UpdateWalletBalanceRequest;
import edu.nikitazubov.walletservice.entity.Wallet;
import edu.nikitazubov.walletservice.exception.WalletNotFoundException;
import edu.nikitazubov.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/wallets")
    ResponseEntity<?> createWallet(@RequestParam("name") String name, @RequestParam("userId") Long userId) {
        Wallet newWallet = walletService.createWallet(name, userId);
        return ResponseEntity.ok(newWallet);
    }

    @GetMapping("/wallets/balance")
    ResponseEntity<?> getWalletBalance(@RequestParam("walletId") Long walletId) {
        try {
            Long balance = walletService.getBalance(walletId);
            return ResponseEntity.ok(balance);
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @PostMapping("/wallets/updateBalance")
    ResponseEntity<?> updateWalletBalance(@RequestBody UpdateWalletBalanceRequest request) {
        try {
            walletService.updateBalance(request);
            return ResponseEntity.ok(request.getNewBalance());
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
