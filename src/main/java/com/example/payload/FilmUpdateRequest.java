package com.example.payload;

import lombok.*;

@Data
public class FilmUpdateRequest {
    private String title;
    private Integer boxOffice;
    private Integer duration;
    private String description;
    private Integer releaseYear;

}
