package com.example.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {
    private final Long id;
    private final String username;
    private final String content;
    private final String createdDate;

}
