package com.example.services;

import com.example.payload.response.JwtAuthenticationResponse;
import com.example.payload.request.LoginRequest;
import com.example.payload.request.RegisterRequest;
import com.example.security.UserPrincipal;

public interface AuthService {
    void registerUser(RegisterRequest registerRequest);
    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);
    void grantAdminRightsToUser(Long userId, UserPrincipal userPrincipal);
}
