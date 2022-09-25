package com.example.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleActorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer height;
    private Integer bornYear;

}
