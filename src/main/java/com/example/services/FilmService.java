package com.example.services;

import com.example.payload.request.FilmUpdateRequest;
import com.example.payload.request.NewCommentRequest;
import com.example.payload.request.NewFilmRequest;
import com.example.payload.response.CommentResponse;
import com.example.payload.response.FullFilmResponse;
import com.example.payload.response.SimpleActorResponse;
import com.example.payload.response.SimpleFilmResponse;
import com.example.specification.FilmSpecification;
import com.example.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmService {
    Page<SimpleFilmResponse> getAllFilms(FilmSpecification filmSpecification, Pageable pageable);
    void newFilm(NewFilmRequest newFilmRequest);
    void updateFilm(Long filmId, FilmUpdateRequest filmUpdateRequest);
    void deleteFilm(Long filmId);
    FullFilmResponse findFilmById(Long filmId);
    void addActorToFilm(Long filmId, Long actorId);
    void deleteActorFromFilm(Long filmId, Long actorId);
    Page<SimpleActorResponse> getFilmActors(Long filmId, Pageable pageable);

}
