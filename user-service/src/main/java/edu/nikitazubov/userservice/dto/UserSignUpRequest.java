package edu.nikitazubov.userservice.dto;

import lombok.Data;

@Data
public class UserSignUpRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
