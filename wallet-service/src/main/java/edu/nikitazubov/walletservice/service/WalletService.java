package edu.nikitazubov.walletservice.service;

import edu.nikitazubov.walletservice.dto.UpdateWalletBalanceRequest;
import edu.nikitazubov.walletservice.entity.Wallet;
import edu.nikitazubov.walletservice.exception.WalletNotFoundException;

public interface WalletService {
    Wallet createWallet(String name, Long userId);
    void updateBalance(UpdateWalletBalanceRequest request) throws WalletNotFoundException;
    Long getBalance(Long walletId) throws WalletNotFoundException;
}
