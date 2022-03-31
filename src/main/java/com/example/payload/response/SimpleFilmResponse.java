package com.example.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SimpleFilmResponse {
    private final Long id;
    private final String title;
    private final Integer releaseYear;
    private final Integer duration;
}
