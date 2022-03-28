package com.example.payload;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleActorResponse {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Integer height;
    private final Integer bornYear;

}
