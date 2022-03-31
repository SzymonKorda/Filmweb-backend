package com.example.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmUpdateRequest {
    private String title;
    private Integer boxOffice;
    private Integer duration;
    private String description;
    private Integer releaseYear;

}
