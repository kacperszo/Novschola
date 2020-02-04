package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.Post;
import io.novschola.model.User;
import io.novschola.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        when(postRepository.findAllByAuthor(any(), any())).thenReturn(new PageImpl<Post>(postList));
        assertEquals(postList, postService.findAllByAuthor(new User(), PageRequest.of(0, 1)).toList());
    }

    @Test
    void findAllByAuthorId() {
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        when(postRepository.findAllByAuthor_Id(any(), any())).thenReturn(new PageImpl<Post>(postList));
        assertEquals(postList, postService.findAllByAuthorId(2L, PageRequest.of(0, 1)).toList());
    }

    @Test
    void search() {
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        when(postRepository.findAllByContentContainingOrTitleContaining(any(), any(), any())).thenReturn(new PageImpl<Post>(postList));
        assertEquals(postList, postService.search("", PageRequest.of(0, 1)).toList());
    }

    @Test
    void update() {
        when(postRepository.save(any())).thenReturn(post);
        assertEquals(post, postService.update(post));
        verify(postRepository, times(1)).save(post);
        post.setId(null);
        assertThrows(BadRequestException.class, () -> postService.update(post));
    }

    @Test
    void create() {
        when(postRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(post));
        postService.create(post, new User());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void delete() {
        postService.delete(post);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void deleteById() {
        postService.deleteById(2L);
        verify(postRepository, times(1)).deleteById(2L);
    }

    @Test
    void findAll() {
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        when(postRepository.findAll((Pageable) any())).thenReturn(new PageImpl<Post>(postList));
        assertEquals(postList, postService.findAll(PageRequest.of(0, 1)).toList());

    }
}