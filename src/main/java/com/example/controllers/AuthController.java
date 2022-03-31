package com.example.controllers;

import com.example.payload.response.JwtAuthenticationResponse;
import com.example.payload.request.LoginRequest;
import com.example.payload.request.RegisterRequest;
import com.example.services.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Api(tags = "Authentication")
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "This endpoint allows to register new user account")
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

    @ApiOperation(value = "This endpoint allows user to log into the application")
    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtAuthenticationResponse jwrResponse = authService.authenticateUser(loginRequest);
        return new ResponseEntity<>(jwrResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to grant admin rights for user (Endpoint created for testing)")
    @PatchMapping("/auth/users/{userId}")
    public ResponseEntity<?> makeAdmin(@PathVariable Long userId) {
        authService.makeAdmin(userId);
        return new ResponseEntity<>("User granted admin rights successfully!", HttpStatus.OK);
    }

}
