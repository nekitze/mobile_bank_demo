package edu.nikitazubov.userservice.service;

import edu.nikitazubov.userservice.dto.JwtResponse;
import edu.nikitazubov.userservice.dto.UserSignInRequest;
import edu.nikitazubov.userservice.dto.UserSignUpRequest;
import edu.nikitazubov.userservice.entity.User;
import edu.nikitazubov.userservice.exception.AlreadyExistsException;
import edu.nikitazubov.userservice.exception.DoesNotExistException;
import edu.nikitazubov.userservice.exception.FailedAuthenticationException;

public interface AuthService {
    JwtResponse signIn(UserSignInRequest request) throws FailedAuthenticationException, DoesNotExistException;

    JwtResponse signUp(UserSignUpRequest request) throws AlreadyExistsException;

    User getUser(String email) throws DoesNotExistException;

    String getUserId(String token);

    void changePassword(String email, String newPasswd) throws DoesNotExistException;
}
