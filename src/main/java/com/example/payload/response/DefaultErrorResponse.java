package com.example.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DefaultErrorResponse {
    private final int status;
    private final String error;
}

