package com.example.payload;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserProfileResponse {
    private final Long id;
    private final String name;
    private final String username;
    private final String email;
    @Builder.Default
    private final List<SimpleFilmResponse> userFilms = new ArrayList<>();

}
