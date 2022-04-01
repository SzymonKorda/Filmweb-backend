package com.example.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class NewFilmRequest {
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 30)
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Box office is mandatory")
    private Integer boxOffice;

    @NotNull(message = "Duration is mandatory")
    private Integer duration;

    @NotNull(message = "Release tear is mandatory")
    private Integer releaseYear;

}
