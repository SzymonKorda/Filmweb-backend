package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.model.Comment;
import com.example.model.Film;
import com.example.payload.CommentResponse;
import com.example.payload.NewCommentRequest;
import com.example.payload.SimpleFilmResponse;
import com.example.repositories.CommentRepository;
import com.example.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Page<CommentResponse> getByFilmId(Pageable pageable, Long filmId) {
        Page<Comment> commentList = commentRepository.findByFilmId(pageable, filmId);
        int totalElements = (int) commentList.getTotalElements();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        return new PageImpl<>(commentList
                .stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getUser().getUsername(),
                        comment.getContent(),
                        formatter.format(Date.from(comment.getCreatedAt()))
                ))
                .collect(Collectors.toList()), pageable, totalElements);

    }

    @Override
    public void deleteCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
        commentRepository.delete(comment);
    }

}
