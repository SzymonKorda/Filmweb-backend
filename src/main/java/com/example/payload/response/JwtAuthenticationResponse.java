package com.example.payload.response;

import com.example.security.UserPrincipal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
