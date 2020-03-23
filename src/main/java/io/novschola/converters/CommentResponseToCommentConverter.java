package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.CommentResponse;
import io.novschola.model.Comment;
import org.springframework.stereotype.Component;

/**
 * Class responsible for converting CommentResponse objects to Comment objects
 *
 * @author Kacper Szot
 */
@Component
public class CommentResponseToCommentConverter implements Converter<CommentResponse, Comment> {

    private UserResponseToUserConverter userResponseToUserConverter;
    private PostResponseToPostConverter postResponseToPostConverter;

    public CommentResponseToCommentConverter(UserResponseToUserConverter userResponseToUserConverter, PostResponseToPostConverter postResponseToPostConverter) {
        this.userResponseToUserConverter = userResponseToUserConverter;
        this.postResponseToPostConverter = postResponseToPostConverter;
    }

    @Override
    public Comment convert(CommentResponse from) {
        return Comment
                .builder()
                .id(from.getId())
                .author(userResponseToUserConverter
                        .convert(from.getAuthor()))
                .post(postResponseToPostConverter
                        .convert(from.getPost()))
                .content(from.getContent())
                .creationTime(from.getCreationTime())
                .build();
    }
}
