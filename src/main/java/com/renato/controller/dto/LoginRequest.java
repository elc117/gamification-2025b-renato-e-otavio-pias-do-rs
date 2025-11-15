package com.renato.controller.dto;

/**
 * DTO para requisição de login.
 */
public class LoginRequest {
    private String email;

    public LoginRequest() {
    }

    public LoginRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
