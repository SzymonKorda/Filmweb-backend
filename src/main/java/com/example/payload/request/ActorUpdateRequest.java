package com.example.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActorUpdateRequest {
    private String firstName;
    private String lastName;
    private String description;
    private String bornPlace;
    private Integer bornYear;
    private Integer height;

}
