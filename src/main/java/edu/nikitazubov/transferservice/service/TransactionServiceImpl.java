package edu.nikitazubov.transferservice.service;

import edu.nikitazubov.transferservice.dto.UpdateWalletBalanceRequest;
import edu.nikitazubov.transferservice.entity.Transaction;
import edu.nikitazubov.transferservice.exception.EmptyResponseBodyException;
import edu.nikitazubov.transferservice.exception.FailedGetWalletBalanceException;
import edu.nikitazubov.transferservice.exception.FailedUpdateBalanceException;
import edu.nikitazubov.transferservice.exception.NotEnoughFundsException;
import edu.nikitazubov.transferservice.feign.WalletClient;
import edu.nikitazubov.transferservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletClient walletClient;

    @Override
    public void performTransaction(Long senderWalletId, Long recipientWalletId, Long amount) throws NotEnoughFundsException, FailedGetWalletBalanceException, EmptyResponseBodyException, FailedUpdateBalanceException {
        boolean creditResponse = credit(senderWalletId, amount);
        boolean debitResponse = debit(recipientWalletId, amount);

        if (creditResponse && debitResponse) {
            Transaction transaction = new Transaction();
            transaction.setSenderWalletId(senderWalletId);
            transaction.setRecipientWalletId(recipientWalletId);
            transaction.setAmount(amount);
            transaction.setCreatedAt(LocalDateTime.now());

            transactionRepository.save(transaction);
        } else {
            throw new FailedUpdateBalanceException("Can't update balance");
        }
    }

    @Override
    public List<Transaction> getTransactionsHistory(Long walletId) {
        return transactionRepository.findTransactionsByWalletId(walletId);
    }

    @Override
    public boolean updateBalance(Long walletId, Long newBalance) {
        UpdateWalletBalanceRequest updateBalanceRequest = new UpdateWalletBalanceRequest(walletId, newBalance);
        ResponseEntity<?> response = walletClient.updateBalance(updateBalanceRequest);

        return response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public boolean credit(Long walletId, Long amount) throws EmptyResponseBodyException, FailedGetWalletBalanceException, NotEnoughFundsException {
        ResponseEntity<?> response = walletClient.getWalletBalance(walletId);

        if (response.getStatusCode().is2xxSuccessful()) {
            if (response.getBody() != null) {
                Long oldBalance = Long.valueOf(response.getBody().toString());
                if (oldBalance < amount) {
                    throw new NotEnoughFundsException("Not enough funds");
                }

                Long newBalance = oldBalance - amount;
                return this.updateBalance(walletId, newBalance);
            } else {
                throw new EmptyResponseBodyException("Empty response body");
            }
        } else {
            throw new FailedGetWalletBalanceException(
                    "Can't get wallet balance :"
                            + " walletId : " + walletId
                            + " status : " + response.getStatusCode());
        }
    }

    @Override
    public boolean debit(Long walletId, Long amount) throws FailedGetWalletBalanceException, EmptyResponseBodyException {
        ResponseEntity<?> response = walletClient.getWalletBalance(walletId);

        if (response.getStatusCode().is2xxSuccessful()) {
            if (response.getBody() != null) {
                Long oldBalance = Long.valueOf(response.getBody().toString());
                Long newBalance = oldBalance + amount;
                return this.updateBalance(walletId, newBalance);
            } else {
                throw new EmptyResponseBodyException("Empty response body");
            }
        } else {
            throw new FailedGetWalletBalanceException(
                    "Can't get wallet balance :"
                            + " walletId : " + walletId
                            + " status : " + response.getStatusCode());
        }
    }
}
