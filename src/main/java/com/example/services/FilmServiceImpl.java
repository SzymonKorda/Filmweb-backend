package com.example.services;

import com.example.mapper.ActorMapper;
import com.example.mapper.CommentMapper;
import com.example.mapper.FilmMapper;
import com.example.specification.FilmSpecification;
import com.example.exceptions.ResourceNotFoundException;
import com.example.exceptions.UniqueConstraintException;
import com.example.model.Actor;
import com.example.model.Comment;
import com.example.model.Film;
import com.example.model.User;
import com.example.payload.*;
import com.example.repositories.ActorRepository;
import com.example.repositories.CommentRepository;
import com.example.repositories.FilmRepository;
import com.example.repositories.UserRepository;
import com.example.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final ActorRepository actorRepository;
    private final CommentRepository commentRepository;

    @Override
    public Page<SimpleFilmResponse> findAllFilms(FilmSpecification filmSpecification, Pageable pageable) {
        Page<Film> filmsListPage = filmRepository.findAll(filmSpecification, pageable);
        return new PageImpl<>(filmsListPage
                .stream()
                .map(FilmMapper::mapFilmToSimpleFilmResponse)
                .collect(Collectors.toList()), pageable, filmsListPage.getTotalElements());
    }

    @Override
    public void newFilm(NewFilmRequest newFilmRequest) {
        try {
            filmRepository.save(prepareFilm(newFilmRequest));
        } catch (RuntimeException ex) {
            throw new UniqueConstraintException("A film with this title and release year already exists in the database");
        }
    }

    @Override
    public void updateFilm(Long filmId, FilmUpdateRequest filmUpdateRequest) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
        filmRepository.save(FilmMapper.mapFilmUpdateRequestToFilm(filmUpdateRequest, film));
    }

    @Override
    public void deleteFilmById(Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
        filmRepository.delete(film);
    }

    @Override
    public FullFilmResponse findFilmById(Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
        return FilmMapper.mapFilmToFullFilmResponse(film);
    }

    @Override
    public Page<SimpleActorResponse> getFilmActors(Long filmId, Pageable pageable) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        Page<Actor> actorsListPage = actorRepository.findAllByFilms(film, pageable);
        return new PageImpl<>(actorsListPage
                .stream()
                .map(ActorMapper::mapActorToSimpleActorResponse)
                .collect(Collectors.toList()), pageable, actorsListPage.getTotalElements());
    }

    @Override
    public void addActorToFilm(Long filmId, Long actorId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId));
        film.getActors().add(actor);
        filmRepository.save(film);
    }


    @Override
    public void addCommentToFilm(UserPrincipal currentUser, Long filmId, NewCommentRequest newCommentRequest) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        Comment comment = Comment.builder()
                .film(film)
                .user(currentUser.getUser())
                .content(newCommentRequest.getContent())
                .build();
        commentRepository.save(comment);
    }

    @Override
    public Page<CommentResponse> getFilmComments(Long filmId, Pageable pageable) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        Page<Comment> commentListPage = commentRepository.findAllByFilm(film, pageable);
        return new PageImpl<>(commentListPage
                .stream()
                .map(CommentMapper::mapCommentToCommentResponse)
                .collect(Collectors.toList()), pageable, commentListPage.getTotalElements());
    }

    @Override
    public void deleteActorFromFilm(Long filmId, Long actorId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        film.getActors().remove(actor);
        filmRepository.save(film);
    }

    private Film prepareFilm(NewFilmRequest newFilmRequest) {
        return Film.builder()
                .title(newFilmRequest.getTitle())
                .description(newFilmRequest.getDescription())
                .duration(newFilmRequest.getDuration())
                .boxOffice(newFilmRequest.getBoxoffice())
                .releaseYear(newFilmRequest.getReleaseYear())
                .build();
    }
}
