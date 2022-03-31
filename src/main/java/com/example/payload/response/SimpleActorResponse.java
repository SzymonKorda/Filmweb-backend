package com.example.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SimpleActorResponse {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Integer height;
    private final Integer bornYear;

}
