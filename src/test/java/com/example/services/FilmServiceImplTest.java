package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.exceptions.UniqueConstraintException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmServiceImplTest {

    private FilmService filmService;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private FilmSpecification filmSpecification;

    @Mock
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        filmService = new FilmServiceImpl(filmRepository, actorRepository);
    }

    private static final Long FILM_ID = 1L;
    private static final Long ACTOR_ID = 1L;
    private static final String FILM_TITLE = "Title1";
    private static final String UPDATED_FILM_TITLE = "Updated Title";
    private static final String FILM_DESCRIPTION = "Description1";
    private static final Integer FILM_DURATION = 120;
    private static final Integer FILM_BOX_OFFICE = 100_000_000;
    private static final Integer FILM_RELEASE_YEAR = 1994;
    private static final String UNIQUE_CONSTRAINT_EXCEPTION_MESSAGE = "A film with this title and release year already exists in the database!";

    @Test
    void getAllFilms() {
        //given
        when(filmRepository.findAll(filmSpecification, pageable)).thenReturn(prepareFilmPage());
        //when
        Page<SimpleFilmResponse> result = filmService.getAllFilms(filmSpecification, pageable);
        //then
        verify(filmRepository, times(1)).findAll(filmSpecification, pageable);
        assertEquals(3, result.getTotalElements());
        assertTrue(result.getContent().get(0) instanceof SimpleFilmResponse);
    }

    @Test
    void newFilm() {
        //given
        ArgumentCaptor<Film> captor = ArgumentCaptor.forClass(Film.class);
        //when
        filmService.newFilm(prepareNewFilmRequest());
        //then
        verify(filmRepository, times(1)).save(captor.capture());
        assertEquals(FILM_TITLE, captor.getValue().getTitle());
        assertEquals(FILM_RELEASE_YEAR, captor.getValue().getReleaseYear());
    }

    @Test
    void shouldThrowUniqueConstraintException_newFilm() {
        //given
        when(filmRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        //when
        Exception expected = null;
        try {
            filmService.newFilm(prepareNewFilmRequest());
        } catch (Exception ex) {
            expected = ex;
        }
        //then
        assertEquals(UNIQUE_CONSTRAINT_EXCEPTION_MESSAGE, expected.getMessage());
        assertTrue(expected instanceof UniqueConstraintException);
    }

    @Test
    void updateFilm() {
        //given
        when(filmRepository.findById(FILM_ID)).thenReturn(Optional.of(prepareFilm()));
        ArgumentCaptor<Film> captor = ArgumentCaptor.forClass(Film.class);
        //when
        filmService.updateFilm(FILM_ID, prepareFilmUpdateRequest());
        //then
        verify(filmRepository, times(1)).save(captor.capture());
        assertEquals(UPDATED_FILM_TITLE, captor.getValue().getTitle());
        assertEquals(FILM_DESCRIPTION, captor.getValue().getDescription());
    }

    @Test
    void shouldThrowResourceNotFoundException_deleteFilm() {
        //given
        //when
        Exception expected = null;
        try {
            filmService.deleteFilm(FILM_ID);
        } catch (Exception ex) {
            expected = ex;
        }
        //then
        assertTrue(expected instanceof ResourceNotFoundException);
    }

    @Test
    void findFilmById() {
        //given
        when(filmRepository.findById(FILM_ID)).thenReturn(Optional.of(prepareFilm()));
        //when
        FullFilmResponse result = filmService.findFilmById(FILM_ID);
        //then
        verify(filmRepository, times(1)).findById(FILM_ID);
        assertEquals(FILM_TITLE, result.getTitle());
        assertTrue(result instanceof FullFilmResponse);
    }

    @Test
    void getFilmActors() {
        //given
        Film film = prepareFilm();
        when(filmRepository.findById(FILM_ID)).thenReturn(Optional.of(film));
        when(actorRepository.findAllByFilms(film, pageable)).thenReturn(prepareActorPage());
        //when
        Page<SimpleActorResponse> result = filmService.getFilmActors(FILM_ID, pageable);
        //then
        verify(actorRepository, times(1)).findAllByFilms(film, pageable);
        assertEquals(3, result.getTotalElements());
        assertTrue(result.getContent().get(0) instanceof SimpleActorResponse);
    }

    @Test
    void addActorToFilm() {
        //given
        when(filmRepository.findById(FILM_ID)).thenReturn(Optional.of(prepareFilm()));
        when(actorRepository.findById(ACTOR_ID)).thenReturn(Optional.of(prepareActor()));
        ArgumentCaptor<Film> captor = ArgumentCaptor.forClass(Film.class);
        //when
        filmService.addActorToFilm(FILM_ID, ACTOR_ID);
        //then
        verify(filmRepository, times(1)).save(captor.capture());
        assertEquals(4, captor.getValue().getActors().size());
    }

    @Test
    void deleteActorFromFilm() {
        //given
        Actor actor = prepareActor();
        Film film = prepareFilm();
        film.getActors().add(actor);
        when(filmRepository.findById(FILM_ID)).thenReturn(Optional.of(film));
        when(actorRepository.findById(ACTOR_ID)).thenReturn(Optional.of(actor));
        ArgumentCaptor<Film> captor = ArgumentCaptor.forClass(Film.class);
        //when
        filmService.deleteActorFromFilm(FILM_ID, ACTOR_ID);
        //then
        verify(filmRepository, times(1)).save(captor.capture());
        assertEquals(3, captor.getValue().getActors().size());
    }

    private Film prepareFilm() {
        return Film.builder()
                .id(FILM_ID)
                .title(FILM_TITLE)
                .description(FILM_DESCRIPTION)
                .duration(FILM_DURATION)
                .boxOffice(FILM_BOX_OFFICE)
                .releaseYear(FILM_RELEASE_YEAR)
                .actors(prepareActorSet())
                .build();
    }

    private Page<Film> prepareFilmPage() {
        List<Film> films = new ArrayList<>();
        films.add(prepareFilm());
        films.add(new Film());
        films.add(new Film());
        return new PageImpl<>(films);
    }

    private NewFilmRequest prepareNewFilmRequest() {
        return NewFilmRequest.builder()
                .title(FILM_TITLE)
                .description(FILM_DESCRIPTION)
                .duration(FILM_DURATION)
                .boxOffice(FILM_BOX_OFFICE)
                .releaseYear(FILM_RELEASE_YEAR)
                .build();
    }

    private FilmUpdateRequest prepareFilmUpdateRequest() {
        return FilmUpdateRequest.builder()
                .title(UPDATED_FILM_TITLE)
                .build();
    }

    private Page<Actor> prepareActorPage() {
        List<Actor> actors = prepareActorList();
        return new PageImpl<>(actors);
    }

    private List<Actor> prepareActorList() {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor());
        actors.add(new Actor());
        actors.add(new Actor());
        return actors;
    }

    private Set<Actor> prepareActorSet() {
        return new HashSet<>(prepareActorList());
    }

    private Actor prepareActor() {
        return Actor.builder()
                .id(ACTOR_ID)
                .build();
    }
}