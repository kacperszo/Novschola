package io.novschola.service;

import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.Post;
import io.novschola.model.User;
import io.novschola.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PostServiceTest {

    @Mock
    PostRepository postRepository;
    PostService postService;
    Post post;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        postService = new PostService(postRepository);


        post = new Post();
        post.setAuthor(new User());
        post.setId(2L);
        post.setContent("Lorem ipusm dolores sit ament");
        post.setCreationTime(LocalDateTime.now());
        post.setTitle("title");
    }

    @Test
    void findById() {
        when(postRepository.findById(2L)).thenReturn(Optional.ofNullable(post));
        when(postRepository.findById(3L)).thenReturn(Optional.empty());
        assertEquals(post, postService.findById(2L));
        assertThrows(ItemNotFoundException.class, () -> postService.findById(3L));
    }

    @Test
    void findAllByAuthor() {
    }

    @Test
    void findAllByAuthorId() {
    }

    @Test
    void search() {
    }

    @Test
    void update() {
    }

    @Test
    void create() {
    }
}