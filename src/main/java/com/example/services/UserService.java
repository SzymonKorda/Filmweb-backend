package com.example.services;

import com.example.payload.response.SimpleFilmResponse;
import com.example.payload.response.UserProfileResponse;
import com.example.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserProfileResponse findUserById(Long userId);
    void addFilmToUser(Long userId, Long filmId, UserPrincipal currentUser);
    Page<SimpleFilmResponse> getUserFilms(Pageable pageable, Long userId);
    void deleteFilmFromUser(Long filmId, Long userId, UserPrincipal currentUser);
}
