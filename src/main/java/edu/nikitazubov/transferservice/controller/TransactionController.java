package edu.nikitazubov.transferservice.controller;

import edu.nikitazubov.transferservice.exception.EmptyResponseBodyException;
import edu.nikitazubov.transferservice.exception.FailedGetWalletBalanceException;
import edu.nikitazubov.transferservice.exception.FailedUpdateBalanceException;
import edu.nikitazubov.transferservice.exception.NotEnoughFundsException;
import edu.nikitazubov.transferservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<?> performTransaction(@RequestParam Long senderWalletId, @RequestParam Long recipientWalletId, @RequestParam Long amount) {
        try {
            transactionService.performTransaction(senderWalletId, recipientWalletId, amount);
            return ResponseEntity.ok().build();
        } catch (NotEnoughFundsException | EmptyResponseBodyException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (FailedGetWalletBalanceException | FailedUpdateBalanceException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/transactions/history")
    public ResponseEntity<?> getTransactionsHistory(@RequestParam("walletId") Long walletId){
        return ResponseEntity.ok(transactionService.getTransactionsHistory(walletId));
    }
}
