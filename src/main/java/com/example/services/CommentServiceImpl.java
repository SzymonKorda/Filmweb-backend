package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.model.Comment;
import com.example.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void deleteCommentById(Long commentId) {
        commentRepository.delete(findComment(commentId));
    }

    private Comment findComment(Long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }

}
