package com.example.services;

import com.example.payload.request.ActorUpdateRequest;
import com.example.payload.request.NewActorRequest;
import com.example.payload.response.FullActorResponse;
import com.example.payload.response.SimpleActorResponse;
import com.example.payload.response.SimpleFilmResponse;
import com.example.specification.ActorSpecification;
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
