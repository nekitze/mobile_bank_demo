package edu.nikitazubov.transferservice.exception;

public class FailedGetWalletBalanceException extends Exception {
    public FailedGetWalletBalanceException(String message){
        super(message);
    }
}
