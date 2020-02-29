package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.model.Post;
import org.springframework.stereotype.Component;

/**
 * Class responsible for converting PostDTO objects to Post objects
 * @author Kacper Szot
 */
@Component
public class PostResponseToPostConverter implements Converter<PostResponse, Post> {
    UserResponseToUserConverter userResponseToUserConverter;

    public PostResponseToPostConverter(UserResponseToUserConverter userResponseToUserConverter) {
        this.userResponseToUserConverter = userResponseToUserConverter;
    }

    @Override
    public Post convert(PostResponse from) {
        Post post = new Post();
        post.setId(from.getId());
        post.setAuthor(userResponseToUserConverter.convert(from.getAuthor()));
        post.setCreationTime(from.getCreationTime());
        post.setTitle(from.getTitle());
        post.setContent(from.getContent());
        return post;
    }
}
