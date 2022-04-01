package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.mapper.CommentMapper;
import com.example.model.Comment;
import com.example.model.Film;
import com.example.payload.request.NewCommentRequest;
import com.example.payload.response.CommentResponse;
import com.example.repositories.CommentRepository;
import com.example.repositories.FilmRepository;
import com.example.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final FilmRepository filmRepository;

    @Override
    public void addCommentToFilm(UserPrincipal currentUser, Long filmId, NewCommentRequest newCommentRequest) {
        commentRepository.save(prepareComment(currentUser, filmId, newCommentRequest));
    }

    @Override
    public void deleteCommentById(Long commentId) {
        commentRepository.delete(findComment(commentId));
    }

    @Override
    public Page<CommentResponse> getFilmComments(Long filmId, Pageable pageable) {
        Page<Comment> commentListPage = commentRepository.findAllByFilm(findFilm(filmId), pageable);
        return new PageImpl<>(commentListPage
                .stream()
                .map(CommentMapper::mapCommentToCommentResponse)
                .collect(Collectors.toList()), pageable, commentListPage.getTotalElements());
    }

    private Comment prepareComment(UserPrincipal currentUser, Long filmId, NewCommentRequest newCommentRequest) {
        return Comment.builder()
                .film(findFilm(filmId))
                .user(currentUser.getUser())
                .content(newCommentRequest.getContent())
                .build();
    }

    private Comment findComment(Long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }

    private Film findFilm(Long filmId) {
        return filmRepository
                .findById(filmId)
                .orElseThrow(() -> new ResourceNotFoundException("Film", "id", filmId));
    }

}
