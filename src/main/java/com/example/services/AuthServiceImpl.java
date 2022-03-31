package com.example.services;

import com.example.exceptions.AppException;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.model.Role;
import com.example.model.RoleName;
import com.example.model.User;
import com.example.payload.response.JwtAuthenticationResponse;
import com.example.payload.request.LoginRequest;
import com.example.payload.request.RegisterRequest;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;
import com.example.security.JwtTokenProvider;
import com.example.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public void registerUser(RegisterRequest registerRequest) {
        checkIfUsernameOrEmailAlreadyExists(registerRequest);
        userRepository.save(createUser(registerRequest));
    }

    @Override
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt, userPrincipal);
    }

    private User createUser(RegisterRequest registerRequest) {
        Role userRole = roleRepository
                .findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        return User.builder()
                .name(registerRequest.getName())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Collections.singleton(userRole))
                .build();
    }

    private void checkIfUsernameOrEmailAlreadyExists(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken!");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email address already in use!");
        }
    }
}
