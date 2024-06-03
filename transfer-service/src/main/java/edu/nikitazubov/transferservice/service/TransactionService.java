package edu.nikitazubov.transferservice.service;

import edu.nikitazubov.transferservice.entity.Transaction;
import edu.nikitazubov.transferservice.exception.EmptyResponseBodyException;
import edu.nikitazubov.transferservice.exception.FailedGetWalletBalanceException;
import edu.nikitazubov.transferservice.exception.FailedUpdateBalanceException;
import edu.nikitazubov.transferservice.exception.NotEnoughFundsException;

import java.util.List;

public interface TransactionService {
    void performTransaction(Long senderWalletId, Long recipientWalletId, Long amount) throws NotEnoughFundsException, FailedGetWalletBalanceException, EmptyResponseBodyException, FailedUpdateBalanceException;

    List<Transaction> getTransactionsHistory(Long walletId);

    boolean credit(Long walletId, Long amount) throws EmptyResponseBodyException, FailedGetWalletBalanceException, NotEnoughFundsException;

    boolean debit(Long walletId, Long amount) throws FailedGetWalletBalanceException, EmptyResponseBodyException;

    boolean updateBalance(Long walletId, Long newBalance);
}
