package edu.nikitazubov.userservice.controller;

import edu.nikitazubov.userservice.dto.JwtResponse;
import edu.nikitazubov.userservice.dto.UserSignInRequest;
import edu.nikitazubov.userservice.dto.UserSignUpRequest;
import edu.nikitazubov.userservice.exception.AlreadyExistsException;
import edu.nikitazubov.userservice.exception.DoesNotExistException;
import edu.nikitazubov.userservice.exception.FailedAuthenticationException;
import edu.nikitazubov.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequest request) {

        try {
            JwtResponse jwtResponse = authService.signUp(request);
            return ResponseEntity.ok(jwtResponse);

        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed : " + e.getMessage());
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody UserSignInRequest request) {
        try {
            JwtResponse jwtResponse = authService.signIn(request);
            return ResponseEntity.ok(jwtResponse);
        } catch (FailedAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getUserId")
    public ResponseEntity<?> getUserId(@RequestParam String token) {
        return ResponseEntity.ok(authService.getUserId(token));
    }

    public ResponseEntity<?> changePassword(String email, String newPasswd) {
        try {
            authService.changePassword(email, newPasswd);
            return ResponseEntity.ok("password changed");
        } catch (DoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
