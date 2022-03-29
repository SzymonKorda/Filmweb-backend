package com.example.services;

import com.example.specification.ActorSpecification;
import com.example.model.Actor;
import com.example.payload.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService {
    void newActor(NewActorRequest newActorRequest);
    Page<SimpleActorResponse> getAllActors(ActorSpecification actorSpecification, Pageable pageable);
    FullActorResponse findActorById(Long actorId);
    void deleteActor(Long actorId);
    void updateActor(Long actorId, ActorUpdateRequest actorUpdateRequest);
    Page<SimpleFilmResponse> getActorFilms(Long actorId, Pageable pageable);
    void addFilmToActor(Long actorId, Long filmId);
    void deleteActorFilm(Long actorId, Long filmId);
}
