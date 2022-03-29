package com.example.payload;

import lombok.Data;

@Data
public class ActorUpdateRequest {
    private String firstName;
    private String lastName;
    private String description;
    private String bornPlace;
    private Integer bornYear;
    private Integer height;

}
