package com.example.controllers;

import com.example.specification.FilmSpecification;
import com.example.payload.ApiResponse;
import com.example.payload.SimpleFilmResponse;
import com.example.payload.UserProfileResponse;
import com.example.services.FilmService;
import com.example.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
public class UserController {

    private FilmService filmService;
    private UserService userService;

    public UserController(FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    @RolesAllowed("ROLE_USER")
    public UserProfileResponse getUserProfile(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @GetMapping("/users/{userId}/films")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public Page<SimpleFilmResponse> getUserFilms(FilmSpecification filmSpecification, Pageable pageable, @PathVariable Long userId) {
        return userService.getUserFilms(filmSpecification, pageable, userId);
    }

    @DeleteMapping("users/{userId}/films/{filmId}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> deleteUserFilm(@PathVariable Long userId, @PathVariable Long filmId) {
        userService.deleteUserFilmById(filmId, userId);
        return ResponseEntity.ok(new ApiResponse(true, "User's film deleted successfully"));
    }

}
