package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.exceptions.UniqueConstraintException;
import com.example.mapper.ActorMapper;
import com.example.mapper.FilmMapper;
import com.example.model.Actor;
import com.example.model.Film;
import com.example.payload.request.FilmUpdateRequest;
import com.example.payload.request.NewFilmRequest;
import com.example.payload.response.FullFilmResponse;
import com.example.payload.response.SimpleActorResponse;
import com.example.payload.response.SimpleFilmResponse;
import com.example.repositories.ActorRepository;
import com.example.repositories.FilmRepository;
import com.example.specification.FilmSpecification;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final ActorRepository actorRepository;

    @Override
    public Page<SimpleFilmResponse> getAllFilms(FilmSpecification filmSpecification, Pageable pageable) {
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
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueConstraintException("A film with this title and release year already exists in the database!");
        }
    }

    @Override
    public void updateFilm(Long filmId, FilmUpdateRequest filmUpdateRequest) {
        filmRepository.save(FilmMapper.mapFilmUpdateRequestToFilm(filmUpdateRequest, findFilm(filmId)));
    }

    @Override
    public void deleteFilm(Long filmId) {
        filmRepository.delete(findFilm(filmId));
    }

    @Override
    public FullFilmResponse findFilmById(Long filmId) {
        return FilmMapper.mapFilmToFullFilmResponse(findFilm(filmId));
    }

    @Override
    public Page<SimpleActorResponse> getFilmActors(Long filmId, Pageable pageable) {
        Page<Actor> actorsListPage = actorRepository.findAllByFilms(findFilm(filmId), pageable);
        return new PageImpl<>(actorsListPage
                .stream()
                .map(ActorMapper::mapActorToSimpleActorResponse)
                .collect(Collectors.toList()), pageable, actorsListPage.getTotalElements());
    }

    @Override
    public void addActorToFilm(Long filmId, Long actorId) {
        Film film = findFilm(filmId);
        film.getActors().add(findActor(actorId));
        filmRepository.save(film);
    }


    @Override
    public void deleteActorFromFilm(Long filmId, Long actorId) {
        Film film = findFilm(filmId);
        film.getActors().remove(findActor(actorId));
        filmRepository.save(film);
    }

    private Film findFilm(Long filmId) {
        return filmRepository
                .findById(filmId)
                .orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
    }

    private Actor findActor(Long actorId) {
        return actorRepository
                .findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId));
    }

    private Film prepareFilm(NewFilmRequest newFilmRequest) {
        return Film.builder()
                .title(newFilmRequest.getTitle())
                .description(newFilmRequest.getDescription())
                .duration(newFilmRequest.getDuration())
                .boxOffice(newFilmRequest.getBoxOffice())
                .releaseYear(newFilmRequest.getReleaseYear())
                .build();
    }
}
