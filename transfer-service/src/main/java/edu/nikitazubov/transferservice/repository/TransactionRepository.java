package edu.nikitazubov.transferservice.repository;

import edu.nikitazubov.transferservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query("SELECT t FROM Transaction t WHERE t.senderWalletId = :walletId OR t.recipientWalletId = :walletId")
    List<Transaction> findTransactionsByWalletId(@Param("walletId") Long walletId);
}
