package com.example.mapper;

import com.example.model.Film;
import com.example.payload.request.FilmUpdateRequest;
import com.example.payload.response.FullFilmResponse;
import com.example.payload.response.SimpleFilmResponse;

public class FilmMapper {
    public static SimpleFilmResponse mapFilmToSimpleFilmResponse(Film film) {
        return SimpleFilmResponse.builder()
                .id(film.getId())
                .title(film.getTitle())
                .releaseYear(film.getReleaseYear())
                .duration(film.getDuration())
                .build();
    }
    public static FullFilmResponse mapFilmToFullFilmResponse(Film film) {
        return FullFilmResponse.builder()
                .id(film.getId())
                .title(film.getTitle())
                .description(film.getDescription())
                .releaseYear(film.getReleaseYear())
                .boxOffice(film.getBoxOffice())
                .duration(film.getDuration())
                .build();
    }

    public static Film mapFilmUpdateRequestToFilm(FilmUpdateRequest request, Film film) {
        if (request.getTitle() != null) {
            film.setTitle(request.getTitle());
        }
        if (request.getBoxOffice() != null) {
            film.setBoxOffice(request.getBoxOffice());
        }
        if (request.getDuration() != null) {
            film.setDuration(request.getDuration());
        }
        if (request.getDescription() != null) {
            film.setDescription(request.getDescription());
        }
        if (request.getReleaseYear() != null) {
            film.setReleaseYear(request.getReleaseYear());
        }
        return film;
    }
}
