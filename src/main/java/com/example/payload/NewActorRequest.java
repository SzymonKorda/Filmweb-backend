package com.example.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewActorRequest {
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Born place is mandatory")
    private String bornPlace;

    @NotNull(message = "Born year name is mandatory")
    private Integer bornYear;

    @NotNull(message = "Height name is mandatory")
    private Integer height;

}
