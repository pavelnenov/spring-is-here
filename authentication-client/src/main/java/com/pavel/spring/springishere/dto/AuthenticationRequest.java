package com.pavel.spring.springishere.dto;

public class AuthenticationRequest {

    private String email;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public AuthenticationRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthenticationRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
