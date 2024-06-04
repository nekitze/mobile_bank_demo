package edu.nikitazubov.walletservice.service;

import edu.nikitazubov.walletservice.dto.UpdateWalletBalanceRequest;
import edu.nikitazubov.walletservice.entity.Wallet;
import edu.nikitazubov.walletservice.exception.WalletNotFoundException;
import edu.nikitazubov.walletservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public Wallet createWallet(String name, Long userId) {
        Wallet wallet = new Wallet();
        wallet.setName(name);
        wallet.setUserId(userId);
        wallet.setBalance(1000L); // Bonus

        return walletRepository.save(wallet);
    }

    @Override
    public void updateBalance(UpdateWalletBalanceRequest request) throws WalletNotFoundException {
        Optional<Wallet> optionalWallet = walletRepository.findById(request.getWalletId());
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get();
            wallet.setBalance(request.getNewBalance());
            walletRepository.save(wallet);
        } else {
            throw new WalletNotFoundException();
        }
    }

    @Override
    public Long getBalance(Long walletId) throws WalletNotFoundException {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get();
            return wallet.getBalance();
        } else {
            throw new WalletNotFoundException();
        }
    }
}
