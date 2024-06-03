package edu.nikitazubov.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    @PostMapping("/updateEmail")
    ResponseEntity<?> updateEmail(@RequestParam("newEmail") String newEmail) {
        return ResponseEntity.ok(newEmail);
    }
}
