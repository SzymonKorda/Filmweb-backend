package com.example.mapper;

import com.example.model.Comment;
import com.example.payload.CommentResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentMapper {
    public static CommentResponse mapCommentToCommentResponse(Comment comment) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return CommentResponse.builder()
                .id(comment.getId())
                .username(comment.getUser().getUsername())
                .content(comment.getContent())
                .createdDate(formatter.format(Date.from(comment.getCreatedAt())))
                .build();
    }
}