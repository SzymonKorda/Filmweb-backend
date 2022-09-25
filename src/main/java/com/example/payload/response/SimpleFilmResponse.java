package com.example.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleFilmResponse {
    private Long id;
    private String title;
    private Integer releaseYear;
    private Integer duration;
}
