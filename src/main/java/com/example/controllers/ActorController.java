package com.example.controllers;

import com.example.config.ApiPageable;
import com.example.payload.request.ActorUpdateRequest;
import com.example.payload.request.NewActorRequest;
import com.example.payload.response.FullActorResponse;
import com.example.payload.response.SimpleActorResponse;
import com.example.payload.response.SimpleFilmResponse;
import com.example.services.ActorService;
import com.example.specification.ActorSpecification;
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
@Api(tags = "Actor")
public class ActorController {

    private final ActorService actorService;

    @ApiOperation(value = "This endpoint allows to retrieve all actors",
            notes = "No authorization needed to access this resource")
    @ApiImplicitParam(name = "search", allowMultiple = true, dataType = "string", paramType = "query", value = "Filtering search results by last name")
    @ApiPageable
    @GetMapping("/actors")
    public ResponseEntity<?> getAllActors(ActorSpecification actorSpecification, @ApiIgnore Pageable pageable) {
        Page<SimpleActorResponse> actors = actorService.getAllActors(actorSpecification, pageable);
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to get full information about given actor",
            notes = "No authorization needed to access this resource")
    @GetMapping("/actors/{actorId}")
    public ResponseEntity<?> getActor(@PathVariable Long actorId) {
        FullActorResponse actor = actorService.findActorById(actorId);
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to retrieve all films for given actor",
            notes = "No authorization needed to access this resource")
    @ApiPageable
    @GetMapping("/actors/{actorId}/films")
    public ResponseEntity<?> getActorFilms(@PathVariable Long actorId, @ApiIgnore Pageable pageable) {
        Page<SimpleFilmResponse> films = actorService.getActorFilms(actorId, pageable);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to create new actor",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/actors")
    public ResponseEntity<?> createActor(@Valid @RequestBody NewActorRequest newActorRequest) {
        actorService.newActor(newActorRequest);
        return new ResponseEntity<>("Actor created successfully!", HttpStatus.CREATED);
    }

    @ApiOperation(value = "This endpoint allows to delete actor",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/actors/{actorId}")
    public ResponseEntity<?> deleteActor(@PathVariable Long actorId) {
        actorService.deleteActor(actorId);
        return new ResponseEntity<>("Actor deleted successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to update actor",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/actors/{actorId}")
    public ResponseEntity<?> updateActor(@PathVariable Long actorId, @Valid @RequestBody ActorUpdateRequest actorUpdateRequest) {
        actorService.updateActor(actorId, actorUpdateRequest);
        return new ResponseEntity<>("Actor updated successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to add film to actor",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/actors/{actorId}/films/{filmId}")
    public ResponseEntity<?> addFilmToActor(@PathVariable Long actorId, @PathVariable Long filmId) {
        actorService.addFilmToActor(actorId, filmId);
        return new ResponseEntity<>("Film added to actor successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to delete film from actor",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/actors/{actorId}/films/{filmId}")
    public ResponseEntity<?> deleteFilmFromActor(@PathVariable Long actorId, @PathVariable Long filmId) {
        actorService.deleteFilmFromActor(actorId, filmId);
        return new ResponseEntity<>("Film deleted from actor successfully!", HttpStatus.OK);
    }

}

