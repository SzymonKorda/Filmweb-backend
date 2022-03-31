package com.example.controllers;

import com.example.config.ApiPageable;
import com.example.payload.response.SimpleFilmResponse;
import com.example.payload.response.UserProfileResponse;
import com.example.services.UserService;
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

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Api(tags = "User")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "This endpoint allows to retrieve information about user",
            notes = "User or Admin rights needed to access this resource")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        UserProfileResponse profile = userService.findUserById(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to retrieve all favourite films for given user",
            notes = "User or Admin rights needed to access this resource")
    @ApiPageable
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/users/{userId}/films")
    public ResponseEntity<?> getUserFilms(@PathVariable Long userId, @ApiIgnore Pageable pageable) {
        Page<SimpleFilmResponse> films = userService.getUserFilms(pageable, userId);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to delete film from user's favourites",
            notes = "User or Admin rights needed to access this resource")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/users/{userId}/films/{filmId}")
    public ResponseEntity<?> deleteFilmFromUser(@PathVariable Long userId, @PathVariable Long filmId) {
        userService.deleteFilmFromUser(filmId, userId);
        return new ResponseEntity<>("User's film deleted successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to add film to user's favourites",
            notes = "User or Admin rights needed to access this resource")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/users/{userId}/films/{filmId}")
    public ResponseEntity<?> addFilmToUser(@PathVariable Long userId, @PathVariable Long filmId) {
        userService.addFilmToUser(userId, filmId);
        return new ResponseEntity<>("Film added to user successfully!", HttpStatus.OK);
    }

}
