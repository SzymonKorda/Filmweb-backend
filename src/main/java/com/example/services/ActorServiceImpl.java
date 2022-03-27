package com.example.services;

import com.example.specification.ActorSpecification;
import com.example.exceptions.ResourceNotFoundException;
import com.example.model.Actor;
import com.example.model.Film;
import com.example.payload.*;
import com.example.repositories.ActorRepository;
import com.example.repositories.FilmRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ActorServiceImpl implements ActorService {

    private ActorRepository actorRepository;
    private FilmRepository filmRepository;

    public ActorServiceImpl(ActorRepository actorRepository, FilmRepository filmRepository) {
        this.actorRepository = actorRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public Actor newActor(NewActorRequest newActorRequest) {
        Actor actor = new Actor();

        actor.setFirstName(newActorRequest.getFirstName());
        actor.setLastName(newActorRequest.getLastName());
        actor.setHeight(newActorRequest.getHeight());
        actor.setBornYear(newActorRequest.getBornYear());
        actor.setDescription(newActorRequest.getDescription());
        actor.setBornPlace(newActorRequest.getBornPlace());

        return actorRepository.save(actor);
    }

    @Override
    public void deleteActorById(Long actorId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(() ->
                new ResourceNotFoundException("Actor", "id", actorId));
        actorRepository.delete(actor);
    }

    @Override
    public Actor updateActor(Long actorId, ActorUpdateRequest actorUpdateRequest) {
        return actorRepository.findById(actorId).map(actor -> {
            if (!(actorUpdateRequest.getFirstName() == null)) {
                actor.setFirstName(actorUpdateRequest.getFirstName());
            }

            if (!(actorUpdateRequest.getLastName() == null)) {
                actor.setLastName(actorUpdateRequest.getLastName());
            }

            if (!(actorUpdateRequest.getDescription() == null)) {
                actor.setDescription(actorUpdateRequest.getDescription());
            }

            if (!(actorUpdateRequest.getBornYear() == null)) {
                actor.setBornYear(actorUpdateRequest.getBornYear());
            }

            if (!(actorUpdateRequest.getBornPlace() == null)) {
                actor.setBornPlace(actorUpdateRequest.getBornPlace());
            }

            if (!(actorUpdateRequest.getHeight() == null)) {
                actor.setHeight(actorUpdateRequest.getHeight());
            }

            return actorRepository.save(actor);
        }).orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId));
    }


    @Override
    public Page<SimpleActorResponse> getAllActors(ActorSpecification actorSpecification, Pageable pageable) {

        Page<Actor> actorsListPage = actorRepository.findAll(actorSpecification, pageable);
        int totalElements = (int) actorsListPage.getTotalElements();


        return new PageImpl<>(actorsListPage
                .stream()
                .map(person -> new SimpleActorResponse(
                        person.getId(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getHeight(),
                        person.getBornYear()))
                .collect(Collectors.toList()), pageable, totalElements);
    }

    @Override
    public FullActorResponse findActorById(Long actorId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException("Actor", "Id", actorId));
        FullActorResponse fullActorResponse = new FullActorResponse();

        fullActorResponse.setId(actor.getId());
        fullActorResponse.setFirstName(actor.getFirstName());
        fullActorResponse.setLastName(actor.getLastName());
        fullActorResponse.setHeight(actor.getHeight());
        fullActorResponse.setDescription(actor.getDescription());
        fullActorResponse.setBornYear(actor.getBornYear());
        fullActorResponse.setBornPlace(actor.getBornPlace());

        return fullActorResponse;
    }

    @Override
    public Page<SimpleActorResponse> getByFilmId(Pageable pageable, Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        List<Actor> actors = film.getActors();
        Page<Actor> actorPage = new PageImpl<>(actors);
        int totalElements = (int) actorPage.getTotalElements();

        return new PageImpl<>(actorPage
                .stream()
                .map(actor -> new SimpleActorResponse(
                        actor.getId(),
                        actor.getFirstName(),
                        actor.getLastName(),
                        actor.getHeight(),
                        actor.getBornYear()
                ))
                .collect(Collectors.toList()), pageable, totalElements);

    }

    @Override
    @Transactional
    public void addFilmToActor(Long actorId, Long filmId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId));
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
        actor.getFilms().add(film);
        film.getActors().add(actor);
    }

    @Override
    @Transactional
    public void deleteActorFilm(Long actorId, Long filmId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException("Actor", "Id", actorId));
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film", "Id", filmId));
        List<Film> films = actor.getFilms();
        for (Film film1 : films) {
            if (film1.getId() == filmId) {
                films.remove(film1);
                film.getActors().remove(actor);
                break;
            }
        }
    }

    @Override
    public Page<ActorChoiceResponse> getActorsChoices(ActorSpecification actorSpecification, Pageable pageable) {


        Page<Actor> actorsListPage = actorRepository.findAll(actorSpecification, pageable);
        int totalElements = (int) actorsListPage.getTotalElements();
        return new PageImpl<>(actorsListPage
                .stream()
                .map(actor -> new ActorChoiceResponse(
                        actor.getId(),
                        actor.getFirstName(),
                        actor.getLastName()))
                .collect(Collectors.toList()), pageable, totalElements);
    }
}
