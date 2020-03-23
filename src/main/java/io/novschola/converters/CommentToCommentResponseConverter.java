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
    private PostToPostResponseConverter postToPostResponseConverter;

    public CommentToCommentResponseConverter(UserToUserResponseConverter userToUserResponseConverter, PostToPostResponseConverter postToPostResponseConverter) {
        this.userToUserResponseConverter = userToUserResponseConverter;
        this.postToPostResponseConverter = postToPostResponseConverter;
    }

    @Override
    public CommentResponse convert(Comment from){
    return CommentResponse
            .builder()
            .author(userToUserResponseConverter.convert(from.getAuthor()))
            .post(postToPostResponseConverter.convert(from.getPost()))
            .id(from.getId())
            .content(from.getContent())
            .creationTime(from.getCreationTime())
            .build();
    }
}
