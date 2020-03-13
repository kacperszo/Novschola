package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.model.Post;
import org.springframework.stereotype.Component;

/**
 * Class responsible for converting Post objects to PostDTO objects
 * @author Kacper Szot
 */
@Component
public class PostToPostResponseConverter implements Converter<Post, PostResponse> {

    private UserToUserResponseConverter userToUserResponseConverter;

    public PostToPostResponseConverter(UserToUserResponseConverter userToUserResponseConverter) {
        this.userToUserResponseConverter = userToUserResponseConverter;
    }

    @Override
    public PostResponse convert(Post from) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(from.getId());
        postResponse.setTitle(from.getTitle());
        postResponse.setCreationTime(from.getCreationTime());
        postResponse.setContent(from.getContent());
        postResponse.setAuthor(userToUserResponseConverter.convert(from.getAuthor()));
        return postResponse;
    }
}
