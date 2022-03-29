package com.example.services;

import com.example.model.Film;
import com.example.specification.FilmSpecification;
import com.example.payload.*;
import com.example.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmService {
    Page<CommentResponse> getFilmComments(Long filmId, Pageable pageable);
    Page<SimpleFilmResponse> findAllFilms(FilmSpecification filmSpecification, Pageable pageable);
    void newFilm(NewFilmRequest newFilmRequest);
    void updateFilm(Long filmId, FilmUpdateRequest filmUpdateRequest);
    void deleteFilmById(Long filmId);
    FullFilmResponse findFilmById(Long filmId);
    void addActorToFilm(Long filmId, Long actorId);
    void addCommentToFilm(UserPrincipal currentUser, Long filmId, NewCommentRequest newCommentRequest);
    void addFilmToUser(UserPrincipal currentUser, Long filmId);
    Page<SimpleFilmResponse> getByActorId(Pageable pageable, Long actorId);
    void deleteActorFromFilm(Long filmId, Long actorId);
    Page<SimpleActorResponse> getFilmActors(Long filmId, Pageable pageable);

}
