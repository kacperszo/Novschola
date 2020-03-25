package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.model.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class responsible for converting PostDTO objects to Post objects
 *
 * @author Kacper Szot
 */
@Component
public class PostResponseToPostConverter implements Converter<PostResponse, Post> {
    private UserResponseToUserConverter userResponseToUserConverter;

    private CommentResponseToCommentConverter commentResponseToCommentConverter;

    public PostResponseToPostConverter(UserResponseToUserConverter userResponseToUserConverter, CommentResponseToCommentConverter commentResponseToCommentConverter) {
        this.userResponseToUserConverter = userResponseToUserConverter;
        this.commentResponseToCommentConverter = commentResponseToCommentConverter;
    }

    @Override
    public Post convert(PostResponse from) {
        if (from == null) {
            return null;
        }
        return Post.builder()
                .id(from.getId())
                .author(userResponseToUserConverter.convert(from.getAuthor()))
                .creationTime(from.getCreationTime())
                .title(from.getTitle())
                .content(from.getContent())
                .comments(from.getComments()
                        .stream()
                        .map(x -> commentResponseToCommentConverter.convert(x))
                        .collect(Collectors.toCollection(ArrayList::new)))
                .build();
    }
}
