package io.novschola.converters;

import io.novschola.api.v1.model.PostDTO;
import io.novschola.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostDTOToPostConverter implements Converter<PostDTO, Post> {
    UserDTOtoUserConverter userDTOtoUserConverter;

    public PostDTOToPostConverter(UserDTOtoUserConverter userDTOtoUserConverter) {
        this.userDTOtoUserConverter = userDTOtoUserConverter;
    }

    @Override
    public Post convert(PostDTO from) {
        Post post = new Post();
        post.setId(from.getId());
        post.setAuthor(userDTOtoUserConverter.convert(from.getAuthor()));
        post.setCreationTime(from.getCreationTime());
        post.setTitle(from.getTitle());
        post.setContent(from.getContent());
        return post;
    }
}
