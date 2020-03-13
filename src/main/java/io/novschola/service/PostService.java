package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.Post;
import io.novschola.model.User;
import io.novschola.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Post service
 * @author Kacper Szot
 */
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
        /* we want to look for posts where title OR content contains given string */
        return postRepository.findAllByContentContainingOrTitleContaining(query, query, pageable);
    }

    public Post update(Post post) {
        if (post.getId() == null || !postRepository.existsById(post.getId())) {
            throw new BadRequestException();
        }
        return postRepository.saveAndFlush(post);

    }

    public Post create(Post post) {
        post.setId(null);
        post.setCreationTime(null);
        return postRepository.saveAndFlush(post);
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

}
