package com.example.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewFilmRequest {
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 30)
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Boxoffice is mandatory")
    private Integer boxoffice;

    @NotNull(message = "Duration is mandatory")
    private Integer duration;

    @NotNull(message = "Release tear is mandatory")
    private Integer releaseYear;

}
