package com.example.services;

import com.example.exceptions.AppException;
import com.example.exceptions.ResourceNotFoundException;
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
import org.springframework.security.access.AccessDeniedException;
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
        Authentication authentication = authenticationManager.authenticate(prepareAuthenticationToken(loginRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt, userPrincipal);
    }

    @Override
    public void grantAdminRightsToUser(Long userId, UserPrincipal currentUser) {
        User user = findUser(userId);
        validateIfCurrentUser(currentUser, user);
        user.getRoles().add(findRole(RoleName.ROLE_ADMIN));
        userRepository.save(user);
    }

    private void validateIfCurrentUser(UserPrincipal currentUser, User user) {
        if (user.getId() != currentUser.getId()) {
            throw new AccessDeniedException("You can't grant admin rights for another user!");
        }
    }

    private User createUser(RegisterRequest registerRequest) {
        return User.builder()
                .name(registerRequest.getName())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Collections.singleton(findRole(RoleName.ROLE_USER)))
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

    private UsernamePasswordAuthenticationToken prepareAuthenticationToken(LoginRequest loginRequest) {
        return new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(),
                loginRequest.getPassword()
        );
    }

    private Role findRole(RoleName roleName) {
        return roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new AppException("User Role not set."));
    }

    private User findUser(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    }
}
