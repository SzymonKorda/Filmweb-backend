package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.exceptions.UniqueConstraintException;
import com.example.model.Actor;
import com.example.model.Film;
import com.example.payload.*;
import com.example.repositories.ActorRepository;
import com.example.repositories.FilmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    private FilmRepository filmRepository;
    private ActorRepository actorRepository;

    public FilmServiceImpl(FilmRepository filmRepository, ActorRepository actorRepository) {
        this.filmRepository = filmRepository;
        this.actorRepository = actorRepository;
    }

    //    @Override
//    public Page<Film> findAllFilms(Pageable pageable) {
//        return filmRepository.findAll(pageable);
//    }

    @Override
    public List<SimpleFilmResponse> findAllFilms() {
        List<Film> filmList = filmRepository.findAll();
        List<SimpleFilmResponse> simpleFilmResponseList = new ArrayList<>();
        filmList.forEach(film -> {
            SimpleFilmResponse simpleFilmResponse = new SimpleFilmResponse();
            simpleFilmResponse.setId(film.getId());
            simpleFilmResponse.setTitle(film.getTitle());
            simpleFilmResponse.setBoxoffice(film.getBoxoffice());
            simpleFilmResponse.setDuration(film.getDuration());
            simpleFilmResponseList.add(simpleFilmResponse);
        });

        return simpleFilmResponseList;
    }

    @Override
    public Film newFilm(NewFilmRequest newFilmRequest) {

        Film film = new Film();
        film.setTitle(newFilmRequest.getTitle());
        film.setDuration(newFilmRequest.getDuration());
        film.setBoxoffice(newFilmRequest.getBoxoffice());

        //musimy "odpakowac" wyjatek i dopiero potem go obsluzyc
        try {
            filmRepository.save(film);
        } catch(RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if(rootCause instanceof SQLException) {
                if("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new UniqueConstraintException("A film with this title exists in the database", rootCause);
                }
            }
        }

        return film;
    }

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

            return filmRepository.save(film);
        }).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
    }

    @Override
    public void deleteFilmById(Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
        filmRepository.delete(film);
    }

    @Override
    public FullFilmResponse findFilmById(Long filmId) {
        FullFilmResponse fullFilmResponse = new FullFilmResponse();
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));

        fullFilmResponse.setTitle(film.getTitle());
        fullFilmResponse.setBoxoffice(film.getBoxoffice());
        fullFilmResponse.setDuration(film.getDuration());
        fullFilmResponse.setActors(film.getActors());


        return fullFilmResponse;
    }

    //TODO taki sam aktor w jednym filmie (HashSet)
    @Override
    @Transactional
    public void addActorToFilm(Long filmId, IdRequest idRequest) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
        Actor actor = actorRepository.findById(idRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("Actor", "id", idRequest.getId()));
        film.getActors().add(actor);
        actor.getFilms().add(film);

        film.setActors(film.getActors());
    }
}
