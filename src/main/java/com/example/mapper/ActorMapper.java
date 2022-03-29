package com.example.mapper;

import com.example.model.Actor;
import com.example.payload.ActorUpdateRequest;
import com.example.payload.FullActorResponse;
import com.example.payload.NewActorRequest;
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

    public static FullActorResponse mapActorToFullActorResponse(Actor actor) {
        return FullActorResponse.builder()
                .id(actor.getId())
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .height(actor.getHeight())
                .description(actor.getDescription())
                .bornYear(actor.getBornYear())
                .bornPlace(actor.getBornPlace())
                .build();
    }

    public static Actor mapNewActorRequestToActor(NewActorRequest request) {
        return Actor.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .height(request.getHeight())
                .bornPlace(request.getBornPlace())
                .description(request.getDescription())
                .bornYear(request.getBornYear())
                .build();
    }

    public static Actor mapActorUpdateRequestToActor(ActorUpdateRequest request, Actor actor) {
        if (request.getFirstName() != null) {
            actor.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            actor.setLastName(request.getLastName());
        }
        if (request.getDescription() != null) {
            actor.setDescription(request.getDescription());
        }
        if (request.getBornYear() != null) {
            actor.setBornYear(request.getBornYear());
        }
        if (request.getBornPlace() != null) {
            actor.setBornPlace(request.getBornPlace());
        }
        if (request.getHeight() != null) {
            actor.setHeight(request.getHeight());
        }
        return actor;
    }
}
