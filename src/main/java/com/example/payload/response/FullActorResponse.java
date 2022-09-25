package com.example.payload.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullActorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String description;
    private String bornPlace;
    private Integer bornYear;
    private Integer height;

}
