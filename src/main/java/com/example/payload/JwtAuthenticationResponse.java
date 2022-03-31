package com.example.payload;

import com.example.model.User;
import com.example.security.UserPrincipal;
import lombok.*;

@Data
public class JwtAuthenticationResponse {
    private final String accessToken;
    private final String tokenType = "Bearer";
    private final Long userId;
    private final String displayName;
    private final boolean isAdmin;

    public JwtAuthenticationResponse(String accessToken, UserPrincipal userPrincipal) {
        this.accessToken = accessToken;
        this.userId = userPrincipal.getId();
        this.displayName = userPrincipal.getUsername();
        this.isAdmin = userPrincipal.isAdmin();
    }

}
