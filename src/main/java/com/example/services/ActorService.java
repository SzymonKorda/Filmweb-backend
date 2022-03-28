package com.example.services;

import com.example.specification.ActorSpecification;
import com.example.model.Actor;
import com.example.payload.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService {
    Actor newActor(NewActorRequest newActorRequest);

    Page<SimpleActorResponse> getAllActors(ActorSpecification actorSpecification, Pageable pageable);

    FullActorResponse findActorById(Long actorId);

    void deleteActorById(Long actorId);

    Actor updateActor(Long actorId, ActorUpdateRequest actorUpdateRequest);

    void addFilmToActor(Long actorId, Long filmId);

    void deleteActorFilm(Long actorId, Long filmId);

    Page<ActorChoiceResponse> getActorsChoices(ActorSpecification actorSpecification, Pageable pageable);
}
