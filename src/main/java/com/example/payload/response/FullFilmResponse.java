package com.example.payload.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullFilmResponse {
    private Long id;
    private String title;
    private String description;
    private Integer boxOffice;
    private Integer duration;
    private Integer releaseYear;

}
