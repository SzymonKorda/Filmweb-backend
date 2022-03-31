package com.example.services;

import com.example.payload.response.JwtAuthenticationResponse;
import com.example.payload.request.LoginRequest;
import com.example.payload.request.RegisterRequest;

public interface AuthService {
    void registerUser(RegisterRequest registerRequest);
    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);
    void makeAdmin(Long userId);
}
