package com.example.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleFilmResponse {
    private final Long id;
    private final String title;
    private final Integer releaseYear;
    private final Integer duration;
}
