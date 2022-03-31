package com.example.mapper;

import com.example.model.User;
import com.example.payload.response.SimpleFilmResponse;
import com.example.payload.response.UserProfileResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserProfileResponse mapUserToUserResponse(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .userFilms(retrieveUserFilms(user))
                .build();
    }

    private static List<SimpleFilmResponse> retrieveUserFilms(User user) {
        return user.getUserFilms().stream()
                .map(FilmMapper::mapFilmToSimpleFilmResponse)
                .collect(Collectors.toList());
    }
}
