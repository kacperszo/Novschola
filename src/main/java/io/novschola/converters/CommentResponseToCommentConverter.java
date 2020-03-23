package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.CommentResponse;
import io.novschola.model.Comment;
import io.novschola.service.CommentService;
import org.springframework.stereotype.Component;

/**
 * Class responsible for converting CommentResponse objects to Comment objects
 *
 * @author Kacper Szot
 */
@Component
public class CommentResponseToCommentConverter implements Converter<CommentResponse, Comment> {

    private UserResponseToUserConverter userResponseToUserConverter;
    private CommentService commentService;

    public CommentResponseToCommentConverter(UserResponseToUserConverter userResponseToUserConverter , CommentService commentService) {
        this.userResponseToUserConverter = userResponseToUserConverter;
        this.commentService = commentService;
    }

    @Override
    public Comment convert(CommentResponse from) {
        Comment comment = commentService.findById(from.getId());
        comment.setId(from.getId());
        comment.setCreationTime(from.getCreationTime());
        comment.setAuthor(userResponseToUserConverter.convert(from.getAuthor()));
        comment.setContent(from.getContent());
        return comment;
    }
}
