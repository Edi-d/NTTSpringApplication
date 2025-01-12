package org.example.authentication.response;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    // Setter (optional, if you want to allow modifications)
    public void setToken(String token) {
        this.token = token;
    }
}
