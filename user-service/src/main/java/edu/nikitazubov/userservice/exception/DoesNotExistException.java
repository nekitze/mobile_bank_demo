package edu.nikitazubov.userservice.exception;

public class DoesNotExistException extends Exception{
    public DoesNotExistException(String message) {
        super(message);
    }
}
