package com.example.payload;

import lombok.*;

@Data
@Builder
public class FullFilmResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final Integer boxOffice;
    private final Integer duration;
    private final Integer releaseYear;

}
