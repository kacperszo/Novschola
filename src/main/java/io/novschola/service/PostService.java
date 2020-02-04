package io.novschola.service;

import io.novschola.model.Post;
import io.novschola.model.User;
import io.novschola.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findById(Long id) {
        return null;
    }

    public List<Post> findAllByAuthor(User author) {
        return null;
    }

    public List<Post> findAllByAuthorId(Long Id) {
        return null;
    }

    public List<Post> search(String query) {
        return null;
    }

    public Post update(Post post) {
        return null;
    }

    public Post create(Post post) {
        return null;
    }
}
