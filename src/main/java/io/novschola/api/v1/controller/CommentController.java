package io.novschola.api.v1.controller;

import io.novschola.api.v1.model.dto.request.CreateCommentRequest;
import io.novschola.api.v1.model.dto.request.UpdatePostRequest;
import io.novschola.api.v1.model.dto.response.CommentResponse;
import io.novschola.converters.CommentToCommentResponseConverter;
import io.novschola.exception.BadRequestException;
import io.novschola.exception.ForbiddenException;
import io.novschola.model.Comment;
import io.novschola.repositories.RoleRepository;
import io.novschola.service.CommentService;
import io.novschola.service.PostService;
import io.novschola.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Comment controller
 */
@RestController
@RequestMapping("v1")
public class CommentController {

    private CommentService commentService;
    private CommentToCommentResponseConverter commentToCommentResponseConverter;
    private PostService postService;
    private UserService userService;

    public CommentController(CommentService commentService, CommentToCommentResponseConverter commentToCommentResponseConverter, PostService postService, UserService userService, RoleRepository roleRepository) {
        this.commentService = commentService;
        this.commentToCommentResponseConverter = commentToCommentResponseConverter;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/posts/{postId}")
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid CreateCommentRequest request, @PathVariable Long postId, BindingResult result, Principal principal) {

        if (result.hasErrors()) {
            throw new BadRequestException(result);
        }

        Comment comment = Comment.builder()
                .content(request.getContent())
                .author(userService.findByEmail(principal.getName()))
                .post(postService.findById(postId))
                .build();

        return new ResponseEntity<>(commentToCommentResponseConverter.convert(commentService.create(comment)), HttpStatus.CREATED);
    }

    @PutMapping("/comments/{commentId}")
    @PostAuthorize("returnObject.getAuthor().getEmail().equals(authentication.name) OR hasRole('ROLE_ADMIN')")
    public CommentResponse update(@RequestBody @Valid UpdatePostRequest request, @PathVariable Long commentId, BindingResult result, Principal principal) {

        if (result.hasErrors()) {
            throw new BadRequestException(result);
        }

        Comment comment = commentService.findById(commentId);
        comment.setContent(request.getContent());

        return commentToCommentResponseConverter.convert(commentService.update(comment));
    }

    @DeleteMapping("/comments/{commentId}")
    public void delete(@PathVariable Long commentId, Authentication authentication) {

        Comment comment = commentService.findById(commentId);

        if (!(authentication.getName().equals(comment.getAuthor().getEmail()) || (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))))) {
            throw new ForbiddenException("Forbidden");
        }

        commentService.delete(comment);
    }
}
