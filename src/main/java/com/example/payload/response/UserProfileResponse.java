package com.example.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class UserProfileResponse {
    private final Long id;
    private final String name;
    private final String username;
    private final String email;
    @Builder.Default
    private final List<SimpleFilmResponse> userFilms = new ArrayList<>();

}
