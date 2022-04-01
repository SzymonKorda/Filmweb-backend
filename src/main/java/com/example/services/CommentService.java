package com.example.services;


import com.example.payload.request.NewCommentRequest;
import com.example.payload.response.CommentResponse;
import com.example.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentResponse> getFilmComments(Long filmId, Pageable pageable);
    void addCommentToFilm(UserPrincipal currentUser, Long filmId, NewCommentRequest newCommentRequest);
    void deleteCommentById(Long commentId);
}
