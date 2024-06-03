package edu.nikitazubov.userservice.exception;

public class FailedAuthenticationException extends Exception{
    public FailedAuthenticationException(String message) {
        super(message);
    }
}
