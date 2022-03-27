package com.example.services;

import com.example.specification.FilmSpecification;
import com.example.payload.SimpleFilmResponse;
import com.example.payload.UserProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserProfileResponse findUserById(Long userId);

    Page<SimpleFilmResponse> getUserFilms(FilmSpecification filmSpecification, Pageable pageable, Long userId);

    void deleteUserFilmById(Long filmId, Long userId);
}
