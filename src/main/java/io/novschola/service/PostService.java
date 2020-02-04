package io.novschola.service;

import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.Post;
import io.novschola.model.User;
import io.novschola.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Page<Post> findAllByAuthor(User author, Pageable pageable) {
        return postRepository.findAllByAuthor(author, pageable);
    }

    public Page<Post> findAllByAuthorId(Long id, Pageable pageable) {
        return postRepository.findAllByAuthor_Id(id, pageable);
    }

    public Page<Post> search(String query, Pageable pageable) {
        return null;
    }

    public Post update(Post post) {
        return null;
    }

    public Post create(Post post) {
        return null;
    }

    public Page<Post> findAll(Pageable pageable) {
        return null;
    }

    public void delete(Post post) {

    }

    public void deleteById() {

    }

}
