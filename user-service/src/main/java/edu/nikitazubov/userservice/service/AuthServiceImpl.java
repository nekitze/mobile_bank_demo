package edu.nikitazubov.userservice.service;

import edu.nikitazubov.userservice.dto.JwtResponse;
import edu.nikitazubov.userservice.dto.UserSignInRequest;
import edu.nikitazubov.userservice.dto.UserSignUpRequest;
import edu.nikitazubov.userservice.entity.Role;
import edu.nikitazubov.userservice.entity.User;
import edu.nikitazubov.userservice.exception.AlreadyExistsException;
import edu.nikitazubov.userservice.exception.DoesNotExistException;
import edu.nikitazubov.userservice.exception.FailedAuthenticationException;
import edu.nikitazubov.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse signIn(UserSignInRequest request) throws FailedAuthenticationException, DoesNotExistException {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var user = userRepository.findUserByEmail(request.getEmail())
                    .orElseThrow(() -> new DoesNotExistException("Email doesn't exist : " + request.getEmail()));

            var jwt = jwtService.generateToken(user);
            JwtResponse jwtAuthResponse = new JwtResponse(jwt);
            jwtAuthResponse.setToken(jwt);

            return jwtAuthResponse;
        }catch (AuthenticationException e){
            throw new FailedAuthenticationException("Authentication failed : " + e.getMessage());
        }

    }

    @Override
    public JwtResponse signUp(UserSignUpRequest request) throws AlreadyExistsException {
        boolean exist = userRepository.existsByEmail(request.getEmail());
        if(!exist){
            User user = new User();
            user.setFirstname( request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.USER);

            userRepository.save(user);
            var jwt = jwtService.generateToken(user);

            return new JwtResponse(jwt);

        }else {
            throw new AlreadyExistsException("Email already exists. " + request.getEmail());
        }
    }

    @Override
    public User getUser(String email) throws DoesNotExistException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new DoesNotExistException("Email doesn't exist : " + email));
    }


    @Override
    public void changePassword(String email, String newPasswd) throws DoesNotExistException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(()-> new DoesNotExistException("Email doesn't exist : " + email));

        user.setPassword(passwordEncoder.encode(newPasswd));
        userRepository.save(user);
    }

    @Override
    public String getUserId(String token) {
        return jwtService.extractUserId(token);
    }
}
