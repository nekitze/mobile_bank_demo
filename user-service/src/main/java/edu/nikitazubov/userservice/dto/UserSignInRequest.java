package edu.nikitazubov.userservice.dto;

import lombok.Data;

@Data
public class UserSignInRequest {
    private String email;
    private String password;
}
