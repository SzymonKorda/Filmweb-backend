package com.example.controllers;

import com.example.services.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Api(tags = "Comment")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "This endpoint allows to delete comment",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>("Comment deleted successfully!", HttpStatus.OK);
    }
}
