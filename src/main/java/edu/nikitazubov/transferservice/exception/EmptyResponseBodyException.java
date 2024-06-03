package edu.nikitazubov.transferservice.exception;

public class EmptyResponseBodyException extends Exception {
    public EmptyResponseBodyException(String message){
        super(message);
    }
}
