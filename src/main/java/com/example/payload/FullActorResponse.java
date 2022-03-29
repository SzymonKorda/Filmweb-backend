package com.example.payload;

import lombok.*;

@Data
@Builder
public class FullActorResponse {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String description;
    private final String bornPlace;
    private final Integer bornYear;
    private final Integer height;

}
