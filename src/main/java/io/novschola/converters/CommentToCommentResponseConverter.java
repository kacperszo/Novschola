package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.CommentResponse;
import io.novschola.model.Comment;
import org.springframework.stereotype.Component;

/**
 * Class responsible for converting Comment objects to CommentResponse objects
 *
 * @author Kacper Szot
 */
@Component
public class CommentToCommentResponseConverter implements Converter<Comment, CommentResponse> {

    private UserToUserResponseConverter userToUserResponseConverter;

    public CommentToCommentResponseConverter(UserToUserResponseConverter userToUserResponseConverter) {
        this.userToUserResponseConverter = userToUserResponseConverter;
    }

    @Override
    public CommentResponse convert(Comment from) {
        if (from == null) {
            return null;
        }
        return CommentResponse
                .builder()
                .author(userToUserResponseConverter.convert(from.getAuthor()))
                .id(from.getId())
                .content(from.getContent())
                .creationTime(from.getCreationTime())
                .build();
    }
}
