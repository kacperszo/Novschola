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
        Post post = new Post();
        post.setId(from.getId());
        post.setAuthor(userResponseToUserConverter.convert(from.getAuthor()));
        post.setCreationTime(from.getCreationTime());
        post.setTitle(from.getTitle());
        post.setContent(from.getContent());
        post.setComments(from.getComments()
                .stream()
                .map(x -> commentResponseToCommentConverter.convert(x))
                .collect(Collectors.toCollection(ArrayList::new)));
        return post;
    }
}
