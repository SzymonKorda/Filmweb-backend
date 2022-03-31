package com.example.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponse {
    private final Long id;
    private final String username;
    private final String content;
    private final String createdDate;

}
