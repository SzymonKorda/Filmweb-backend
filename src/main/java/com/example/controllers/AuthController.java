package com.example.controllers;

import com.example.payload.response.JwtAuthenticationResponse;
import com.example.payload.request.LoginRequest;
import com.example.payload.request.RegisterRequest;
import com.example.security.CurrentUser;
import com.example.security.UserPrincipal;
import com.example.services.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Api(tags = "Authentication")
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "This endpoint allows to register new user's account",
            notes = "No authorization needed to access this resource")
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

    @ApiOperation(value = "This endpoint allows to log into the application",
            notes = "No authorization needed to access this resource")
    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtAuthenticationResponse jwtResponse = authService.authenticateUser(loginRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to grant Admin rights for User (Endpoint created for testing purposes)",
            notes = "User or Admin rights needed to access this resource")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PatchMapping("/auth/users/{userId}")
    public ResponseEntity<?> grantAdminRightsToUser(@PathVariable Long userId, @ApiIgnore @CurrentUser UserPrincipal currentUser) {
        authService.grantAdminRightsToUser(userId, currentUser);
        return new ResponseEntity<>("User granted admin rights successfully!", HttpStatus.OK);
    }

}
