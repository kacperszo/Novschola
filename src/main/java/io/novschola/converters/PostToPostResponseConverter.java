package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.CommentResponse;
import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.model.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Class responsible for converting Post objects to PostDTO objects
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
        PostResponse postResponse = new PostResponse();
        postResponse.setId(from.getId());
        postResponse.setTitle(from.getTitle());
        postResponse.setCreationTime(from.getCreationTime());
        postResponse.setContent(from.getContent());
        postResponse.setAuthor(userToUserResponseConverter.convert(from.getAuthor()));
        postResponse.setComments(
                from.getComments()
                .stream()
                .map(x -> commentToCommentResponseConverter.convert(x))
                .collect(Collectors.toCollection(ArrayList::new)));
        return postResponse;
    }
}
