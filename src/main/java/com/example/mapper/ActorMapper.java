package com.example.mapper;

import com.example.model.Actor;
import com.example.payload.SimpleActorResponse;

public class ActorMapper {
    public static SimpleActorResponse mapActorToSimpleActorResponse(Actor actor) {
        return SimpleActorResponse.builder()
                .id(actor.getId())
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .bornYear(actor.getBornYear())
                .height(actor.getHeight())
                .build();
    }
}
