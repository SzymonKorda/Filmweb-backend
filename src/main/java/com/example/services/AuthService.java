package com.example.services;

import com.example.payload.JwtAuthenticationResponse;
import com.example.payload.LoginRequest;
import com.example.payload.RegisterRequest;

public interface AuthService {
    void registerUser(RegisterRequest registerRequest);
    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);
}
