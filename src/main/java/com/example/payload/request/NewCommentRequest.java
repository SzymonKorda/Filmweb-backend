package com.example.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NewCommentRequest {
    @NotBlank
    private String content;

}
