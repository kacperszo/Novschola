package io.novschola.converters;

import io.novschola.api.v1.model.PostDTO;
import io.novschola.model.Post;
import org.springframework.stereotype.Component;

/**
 * Class responsible for converting Post objects to PostDTO objects
 * @author Kacper Szot
 */
@Component
public class PostToPostDTOConverter implements Converter<Post, PostDTO> {

    private UserToUserDTOConverter userToUserDTOConverter;

    public PostToPostDTOConverter(UserToUserDTOConverter userToUserDTOConverter) {
        this.userToUserDTOConverter = userToUserDTOConverter;
    }

    @Override
    public PostDTO convert(Post from) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(from.getId());
        postDTO.setTitle(from.getTitle());
        postDTO.setCreationTime(from.getCreationTime());
        postDTO.setContent(from.getContent());
        postDTO.setAuthor(userToUserDTOConverter.convert(from.getAuthor()));
        return postDTO;
    }
}
