package com.example.controllers;

import com.example.config.ApiPageable;
import com.example.payload.request.NewCommentRequest;
import com.example.payload.response.CommentResponse;
import com.example.security.CurrentUser;
import com.example.security.UserPrincipal;
import com.example.services.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Api(tags = "Comment")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "This endpoint allows to retrieve comments for given film",
            notes = "No authorization needed to access this resource")
    @ApiPageable
    @GetMapping("/comments/films/{filmId}")
    public ResponseEntity<?> getFilmComments(@PathVariable Long filmId, @ApiIgnore Pageable pageable) {
        Page<CommentResponse> comments = commentService.getFilmComments(filmId, pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to add comment to film",
            notes = "User or Admin rights needed to access this resource")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/comments/films/{filmId}")
    public ResponseEntity<?> addCommentToFilm(@ApiIgnore @CurrentUser UserPrincipal currentUser, @PathVariable Long filmId, @Valid @RequestBody NewCommentRequest newCommentRequest) {
        commentService.addCommentToFilm(currentUser, filmId, newCommentRequest);
        return new ResponseEntity<>("Comment added to film successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint allows to delete comment",
            notes = "Admin rights needed to access this resource")
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>("Comment deleted successfully!", HttpStatus.OK);
    }
}
