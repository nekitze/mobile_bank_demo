package edu.nikitazubov.walletservice.repository;

import edu.nikitazubov.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findWalletsByUserId(Long userId);
}
