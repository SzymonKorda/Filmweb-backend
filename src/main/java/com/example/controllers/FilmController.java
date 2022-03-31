package com.example.controllers;

import com.example.config.ApiPageable;
import com.example.payload.request.FilmUpdateRequest;
import com.example.payload.request.NewCommentRequest;
import com.example.payload.request.NewFilmRequest;
import com.example.payload.response.CommentResponse;
import com.example.payload.response.FullFilmResponse;
import com.example.payload.response.SimpleActorResponse;
import com.example.payload.response.SimpleFilmResponse;
import com.example.security.CurrentUser;
import com.example.security.UserPrincipal;
import com.example.services.FilmService;
import com.example.specification.FilmSpecification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Api(tags = "Film")
public class FilmController {

    private final FilmService filmService;

    @ApiOperation(value = "This endpoint allows to retrieve all films",
            notes = "No authorization needed to access this resource")
    @ApiImplicitParam(name = "search", allowMultiple = true, dataType = "string", paramType = "query", value = "Filtering search results by title")
    @ApiPageable
    @GetMapping("/films")
    public ResponseEntity<?> getAllFilms(FilmSpecification filmSpecification, @ApiIgnore Pageable pageable) {
        Page<SimpleFilmResponse> films = filmService.getAllFilms(filmSpecification, pageable);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to retrieve comments for given film",
            notes = "No authorization needed to access this resource")
    @ApiPageable
    @GetMapping("/films/{filmId}/comments")
    public ResponseEntity<?> getFilmComments(@PathVariable Long filmId, @ApiIgnore Pageable pageable) {
        Page<CommentResponse> comments = filmService.getFilmComments(filmId, pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to retrieve all actors for given film",
            notes = "No authorization needed to access this resource")
    @ApiPageable
    @GetMapping("/films/{filmId}/actors")
    public ResponseEntity<?> getFilmActors(@PathVariable Long filmId, @ApiIgnore Pageable pageable) {
        Page<SimpleActorResponse> actors = filmService.getFilmActors(filmId, pageable);
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to retrieve full information about given film",
            notes = "No authorization needed to access this resource")
    @GetMapping("/films/{filmId}")
    public ResponseEntity<?> getFilm(@PathVariable Long filmId) {
        FullFilmResponse film = filmService.findFilmById(filmId);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to create new film",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/films")
    public ResponseEntity<?> createFilm(@Valid @RequestBody NewFilmRequest newFilmRequest) {
        filmService.newFilm(newFilmRequest);
        return new ResponseEntity<>("Film Created successfully!", HttpStatus.CREATED);
    }

    @ApiOperation(value = "This endpoint allows to update film",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/films/{filmId}")
    public ResponseEntity<?> updateFilm(@PathVariable Long filmId, @Valid @RequestBody FilmUpdateRequest filmUpdateRequest) {
        filmService.updateFilm(filmId, filmUpdateRequest);
        return new ResponseEntity<>("Film updated successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to delete film",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/films/{filmId}")
    public ResponseEntity<?> deleteFilm(@PathVariable Long filmId) {
        filmService.deleteFilm(filmId);
        return new ResponseEntity<>("Film deleted successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to add actor to film",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/films/{filmId}/actors/{actorId}")
    public ResponseEntity<?> addActorToFilm(@PathVariable Long filmId, @PathVariable Long actorId) {
        filmService.addActorToFilm(filmId, actorId);
        return new ResponseEntity<>("Actor added to film successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to add comment to film",
            notes = "User or Admin rights needed to access this resource")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/films/{filmId}/comments")
    public ResponseEntity<?> addCommentToFilm(@ApiIgnore @CurrentUser UserPrincipal currentUser, @PathVariable Long filmId, @Valid @RequestBody NewCommentRequest newCommentRequest) {
        filmService.addCommentToFilm(currentUser, filmId, newCommentRequest);
        return new ResponseEntity<>("Comment added to film successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to delete actor from film",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/films/{filmId}/actors/{actorId}")
    public ResponseEntity<?> deleteActorFromFilm(@PathVariable Long filmId, @PathVariable Long actorId) {
        filmService.deleteActorFromFilm(filmId, actorId);
        return new ResponseEntity<>("Actor deleted from film successfully!", HttpStatus.OK);
    }

}


