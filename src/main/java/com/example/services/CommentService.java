package com.example.services;

import com.example.payload.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {
    void deleteCommentById(Long commentId);
}
