package com.example.services;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {

    private FilmRepository filmRepository;
    private ActorRepository actorRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;

    public FilmServiceImpl(FilmRepository filmRepository, ActorRepository actorRepository,
                           CommentRepository commentRepository, UserRepository userRepository) {
        this.filmRepository = filmRepository;
        this.actorRepository = actorRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<SimpleFilmResponse> findAllFilms(Pageable pageable) {

        Page<Film> filmsListPage = filmRepository.findAll(pageable);
        int totalElements = (int) filmsListPage.getTotalElements();
        return new PageImpl<>(filmsListPage
                .stream()
                .map(film -> new SimpleFilmResponse(
                        film.getId(),
                        film.getTitle(),
                        film.getPremiereYear(),
                        film.getDuration()))
                .collect(Collectors.toList()), pageable, totalElements);
    }

//    @Override
//    public List<SimpleFilmResponse> findAllFilms() {
//        List<Film> filmList = filmRepository.findAll();
//        List<SimpleFilmResponse> simpleFilmResponseList = new ArrayList<>();
//        filmList.forEach(film -> {
//            SimpleFilmResponse simpleFilmResponse = new SimpleFilmResponse();
//            simpleFilmResponse.setId(film.getId());
//            simpleFilmResponse.setTitle(film.getTitle());
//            simpleFilmResponse.setBoxoffice(film.getBoxoffice());
//            simpleFilmResponse.setDuration(film.getDuration());
//            simpleFilmResponseList.add(simpleFilmResponse);
//        });
//
//        return simpleFilmResponseList;
//    }


    @Override
    public void newFilm(NewFilmRequest newFilmRequest) {

        Film film = new Film();
        film.setTitle(newFilmRequest.getTitle());
        film.setDescription(newFilmRequest.getDescription());
        film.setDuration(newFilmRequest.getDuration());
        film.setBoxoffice(newFilmRequest.getBoxoffice());
        film.setPremiereYear(newFilmRequest.getPremiereYear());

        try {
            filmRepository.save(film);
        } catch (RuntimeException ex) {
            throw new UniqueConstraintException("A film with this title exists in the database");
        }
    }



//    @Override
//    public Film newFilm(NewFilmRequest newFilmRequest) {
//
//        Film film = new Film();
//        film.setTitle(newFilmRequest.getTitle());
//        film.setDuration(newFilmRequest.getDuration());
//        film.setBoxoffice(newFilmRequest.getBoxoffice());
//
//        //musimy "odpakowac" wyjatek i dopiero potem go obsluzyc
//        try {
//            filmRepository.save(film);
//        } catch(RuntimeException e) {
//            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
//            if(rootCause instanceof SQLException) {
//                if("23505".equals(((SQLException) rootCause).getSQLState())) {
//                    throw new UniqueConstraintException("A film with this title exists in the database", rootCause);
//                }
//            }
//        }
//
//        return film;
//    }

    @Override
    public Film updateFilm(Long filmId, FilmUpdateRequest filmUpdateRequest) {

        return filmRepository.findById(filmId).map(film -> {
            if(!(filmUpdateRequest.getTitle() == null)) {
                film.setTitle(filmUpdateRequest.getTitle());
            }

            if(!(filmUpdateRequest.getBoxoffice() == null)) {
                film.setBoxoffice(filmUpdateRequest.getBoxoffice());
            }

            if(!(filmUpdateRequest.getDuration() == null)) {
                film.setDuration(filmUpdateRequest.getDuration());
            }

            if(!(filmUpdateRequest.getDescription() == null)) {
                film.setDescription(filmUpdateRequest.getDescription());
            }

            if(!(filmUpdateRequest.getPremiereYear() == null)) {
                film.setPremiereYear(filmUpdateRequest.getPremiereYear());
            }

            return filmRepository.save(film);
        }).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
    }

    @Override
    public void deleteFilmById(Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() ->
                new ResourceNotFoundException("Film", "id", filmId));
        filmRepository.delete(film);
    }

    @Override
    public FullFilmResponse findFilmById(Long filmId) {
        FullFilmResponse fullFilmResponse = new FullFilmResponse();
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));

        fullFilmResponse.setTitle(film.getTitle());
        fullFilmResponse.setBoxoffice(film.getBoxoffice());
        fullFilmResponse.setDuration(film.getDuration());
        fullFilmResponse.setId(film.getId());
        fullFilmResponse.setDescription(film.getDescription());
        fullFilmResponse.setPremiereYear(film.getPremiereYear());

//        for(Actor actor : film.getActors()) {
//            SimpleActorResponse simpleActorResponse = new SimpleActorResponse();
//            simpleActorResponse.setId(actor.getId());
//            simpleActorResponse.setFirstname(actor.getFirstname());
//            simpleActorResponse.setLastname(actor.getLastname());
//            simpleActorResponse.setHeight(actor.getHeight());
//            fullFilmResponse.getActors().add(simpleActorResponse);
//        }

//        for(Comment comment : film.getComments()) {
//            CommentResponse commentResponse = new CommentResponse();
//            commentResponse.setId(comment.getId());
//            commentResponse.setContent(comment.getContent());
//            User user = userRepository.findById(comment.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("User", "Id", comment.getUser().getId()));
//            commentResponse.setUsername(user.getUsername());
//            fullFilmResponse.getComments().add(commentResponse);
//        }

        return fullFilmResponse;
    }


    //TODO taki sam aktor w jednym filmie (HashSet)
    @Override
    @Transactional
    public void addActorToFilm(Long filmId, Long actorId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId));
        film.getActors().add(actor);
    }


    @Override
    @Transactional
    public void addCommentToFilm(UserPrincipal currentUser, Long filmId, NewCommentRequest newCommentRequest) {
        Comment comment = new Comment();
        comment.setFilmId(filmId);
        comment.setUser(currentUser.getUser());
        comment.setContent(newCommentRequest.getContent());

        commentRepository.save(comment);

        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        film.getComments().add(comment);
    }

    @Override
    public Page<SimpleFilmResponse> getByActorId(Pageable pageable, Long actorId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException("Actor", "Id", actorId));
        List<Film> films = actor.getFilms();
        Page<Film> filmPage = new PageImpl<>(films);
        int totalElements = (int) filmPage.getTotalElements();

        return new PageImpl<>(filmPage
                .stream()
                .map(film -> new SimpleFilmResponse(
                        film.getId(),
                        film.getTitle(),
                        film.getPremiereYear(),
                        film.getDuration()
                ))
                .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    @Transactional
    public void addFilmToUser(UserPrincipal currentUser, Long filmId) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "Id", currentUser.getId()));
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        user.getUserFilms().add(film);
//        film.getUsers().add(user);
    }
}
