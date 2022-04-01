package com.example.payload.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmUpdateRequest {
    private String title;
    private Integer boxOffice;
    private Integer duration;
    private String description;
    private Integer releaseYear;

}
