package com.example.controllers;

import com.example.config.ApiPageable;
import com.example.specification.FilmSpecification;
import com.example.payload.*;
import com.example.security.CurrentUser;
import com.example.security.UserPrincipal;
import com.example.services.ActorService;
import com.example.services.CommentService;
import com.example.services.FilmService;
import io.swagger.annotations.Api;
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
    private final ActorService actorService;
    private final CommentService commentService;

//    TODO: Describe search parameters, default value and search param to add
    @ApiOperation(value = "This endpoint allows to retrieve all films with given parameters")
    @ApiPageable
    @GetMapping("/films")
    public ResponseEntity<?> getFilms(FilmSpecification filmSpecification, @ApiIgnore Pageable pageable) {
        Page<SimpleFilmResponse> films = filmService.findAllFilms(filmSpecification, pageable);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("films/{filmId}/comments")
    public Page<CommentResponse> getComments(@PathVariable Long filmId, Pageable pageable) {
        return commentService.getByFilmId(pageable, filmId);
    }

    @ApiOperation(value = "This endpoint allows to retrieve all actors for given film")
    @ApiPageable
    @GetMapping("films/{filmId}/actors")
    public ResponseEntity<?> getFilmActors(@PathVariable Long filmId, @ApiIgnore Pageable pageable) {
        Page<SimpleActorResponse> actors = filmService.getFilmActors(filmId, pageable);
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to retrieve full film information")
    @GetMapping("/films/{filmId}")
    public ResponseEntity<?> getFilm(@PathVariable Long filmId) {
        FullFilmResponse film = filmService.findFilmById(filmId);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows admin to create new film")
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/films")
    public ResponseEntity<?> createFilm(@Valid @RequestBody NewFilmRequest newFilmRequest) {
        filmService.newFilm(newFilmRequest);
        return new ResponseEntity<>("Film Created successfully", HttpStatus.CREATED);
    }

    @ApiOperation(value = "This endpoint allows admin to update existing film")
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/films/{filmId}")
    public ResponseEntity<?> updateFilm(@PathVariable Long filmId, @Valid @RequestBody FilmUpdateRequest filmUpdateRequest) {
        filmService.updateFilm(filmId, filmUpdateRequest);
        return new ResponseEntity<>("Film updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows admin to delete existing film")
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/films/{filmId}")
    public ResponseEntity<?> deleteFilm(@PathVariable Long filmId) {
        filmService.deleteFilmById(filmId);
        return new ResponseEntity<>("Film deleted successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to add actor to film")
    @PostMapping("/films/{filmId}/actors/{actorId}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> addActorToFilm(@PathVariable Long filmId, @PathVariable Long actorId) {
        filmService.addActorToFilm(filmId, actorId);
        return new ResponseEntity<>("Actor added to film successfully", HttpStatus.OK);
    }

    @PostMapping("films/{filmId}/comments")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> addCommentToFilm(@CurrentUser UserPrincipal currentUser, @PathVariable Long filmId, @Valid @RequestBody NewCommentRequest newCommentRequest) {
        filmService.addCommentToFilm(currentUser, filmId, newCommentRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Comment added to film successfully"));
    }

    @PostMapping("/films/{filmId}/favourites")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> addFilmToUser(@CurrentUser UserPrincipal currentUser, @PathVariable Long filmId) {
        filmService.addFilmToUser(currentUser, filmId);
        return ResponseEntity.ok(new ApiResponse(true, "Film added to user successfully"));
    }

    @DeleteMapping("/films/{filmId}/actors/{actorId}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> deleteActorFromFilm(@PathVariable Long filmId, @PathVariable Long actorId) {
        filmService.deleteActorFromFilm(filmId, actorId);
        return ResponseEntity.ok(new ApiResponse(true, "Film's actor deleted successfully"));
    }

    @GetMapping("/films/choices")
    public Page<FilmChoiceResponse> getFilmsChoices(FilmSpecification filmSpecification, Pageable pageable) {
        return filmService.getFilmsChoices(filmSpecification, pageable);
    }
}


