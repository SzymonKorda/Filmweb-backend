package com.example.services;

import com.example.mapper.ActorMapper;
import com.example.mapper.FilmMapper;
import com.example.payload.request.ActorUpdateRequest;
import com.example.payload.request.NewActorRequest;
import com.example.payload.response.CustomPageImpl;
import com.example.payload.response.FullActorResponse;
import com.example.payload.response.SimpleActorResponse;
import com.example.payload.response.SimpleFilmResponse;
import com.example.specification.ActorSpecification;
import com.example.exceptions.ResourceNotFoundException;
import com.example.model.Actor;
import com.example.model.Film;
import com.example.repositories.ActorRepository;
import com.example.repositories.FilmRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;

    @CacheEvict(cacheNames = "actors", allEntries = true)
    @Override
    public void newActor(NewActorRequest newActorRequest) {
        actorRepository.save(ActorMapper.mapNewActorRequestToActor(newActorRequest));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "actors", allEntries = true),
            @CacheEvict(cacheNames = "actor", key = "#actorId"),
            @CacheEvict(cacheNames = "filmActors", allEntries = true),
            @CacheEvict(cacheNames = "actorFilms", key = "#actorId")
    })
    @Override
    public void deleteActor(Long actorId) {
        actorRepository.delete(findActor(actorId));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "actors", allEntries = true),
            @CacheEvict(cacheNames = "actor", key = "#actorId"),
            @CacheEvict(cacheNames = "filmActors", allEntries = true)
    })
    @Override
    public void updateActor(Long actorId, ActorUpdateRequest actorUpdateRequest) {
        actorRepository.save(ActorMapper.mapActorUpdateRequestToActor(actorUpdateRequest, findActor(actorId)));
    }


    @Cacheable(cacheNames = "actors")
    @Override
    public Page<SimpleActorResponse> getAllActors(ActorSpecification actorSpecification, Pageable pageable) {
        Page<Actor> actorsListPage = actorRepository.findAll(actorSpecification, pageable);
        return new CustomPageImpl<>(actorsListPage
                .stream()
                .map(ActorMapper::mapActorToSimpleActorResponse)
                .collect(Collectors.toList()), pageable, actorsListPage.getTotalElements());
    }
    @Cacheable(cacheNames = "actorFilms", key = "#actorId")
    @Override
    public Page<SimpleFilmResponse> getActorFilms(Long actorId, Pageable pageable) {
        Page<Film> actorsPageList = filmRepository.findAllByActors(findActor(actorId), pageable);
        return new CustomPageImpl<>(actorsPageList
                .stream()
                .map(FilmMapper::mapFilmToSimpleFilmResponse)
                .collect(Collectors.toList()), pageable, actorsPageList.getTotalElements());
    }

    @Cacheable(cacheNames = "actor", key = "#actorId")
    @Override
    public FullActorResponse findActorById(Long actorId) {
        return ActorMapper.mapActorToFullActorResponse(findActor(actorId));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "actorFilms", key = "#actorId"),
            @CacheEvict(cacheNames = "filmActors", key = "#filmId")
    })
    @Override
    public void addFilmToActor(Long actorId, Long filmId) {
        Actor actor = findActor(actorId);
        Film film = findFilm(filmId);
        actor.getFilms().add(film);
        film.getActors().add(actor);
        actorRepository.save(actor);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "actorFilms", key = "#actorId"),
            @CacheEvict(cacheNames = "filmActors", key = "#filmId")
    })
    @Override
    public void deleteFilmFromActor(Long actorId, Long filmId) {
        Actor actor = findActor(actorId);
        Film film = findFilm(filmId);
        actor.getFilms().remove(film);
        film.getActors().remove(actor);
        actorRepository.save(actor);
    }

    private Actor findActor(Long actorId) {
        return actorRepository
                .findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId));
    }

    private Film findFilm(Long filmId) {
        return filmRepository
                .findById(filmId)
                .orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
    }
}
