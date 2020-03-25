package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.model.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class responsible for converting Post objects to PostDTO objects
 *
 * @author Kacper Szot
 */
@Component
public class PostToPostResponseConverter implements Converter<Post, PostResponse> {

    private UserToUserResponseConverter userToUserResponseConverter;
    private CommentToCommentResponseConverter commentToCommentResponseConverter;

    public PostToPostResponseConverter(UserToUserResponseConverter userToUserResponseConverter, CommentToCommentResponseConverter commentToCommentResponseConverter) {
        this.userToUserResponseConverter = userToUserResponseConverter;
        this.commentToCommentResponseConverter = commentToCommentResponseConverter;
    }

    @Override
    public PostResponse convert(Post from) {
        if (from == null) {
            return null;
        }
        return PostResponse.builder()
                .id(from.getId())
                .title(from.getTitle())
                .creationTime(from.getCreationTime())
                .content(from.getContent())
                .author(userToUserResponseConverter.convert(from.getAuthor()))
                .comments(from.getComments()
                        .stream()
                        .map(x -> commentToCommentResponseConverter.convert(x))
                        .collect(Collectors.toCollection(ArrayList::new)))
                .build();
    }
}
