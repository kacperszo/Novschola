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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        postService = new PostService(postRepository);
    }

    @Test
    void findById() {
        //given
        Post post = new Post();
        post.setAuthor(new User());
        post.setId(2L);
        post.setContent("Lorem ipusm dolores sit ament");
        post.setCreationTime(LocalDateTime.now());
        post.setTitle("title");
        //when
        when(postRepository.findById(2L)).thenReturn(Optional.ofNullable(post));
        when(postRepository.findById(3L)).thenReturn(Optional.empty());
        //then
        assertEquals(post, postService.findById(2L));
        assertThrows(ItemNotFoundException.class, () -> postService.findById(3L));
    }

    @Test
    void findAllByAuthor() {
        //given
        Post post = new Post();
        post.setAuthor(new User());
        post.setId(2L);
        post.setContent("Lorem ipusm dolores sit ament");
        post.setCreationTime(LocalDateTime.now());
        post.setTitle("title");
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        //when
        when(postRepository.findAllByAuthor(any(), any())).thenReturn(new PageImpl<Post>(postList));
        //then
        assertEquals(postList, postService.findAllByAuthor(new User(), PageRequest.of(0, 1)).toList());
    }

    @Test
    void findAllByAuthorId() {
        //given
        Post post = new Post();
        post.setAuthor(new User());
        post.setId(2L);
        post.setContent("Lorem ipusm dolores sit ament");
        post.setCreationTime(LocalDateTime.now());
        post.setTitle("title");
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        //when
        when(postRepository.findAllByAuthorId(any(), any())).thenReturn(new PageImpl<Post>(postList));
        //then
        assertEquals(postList, postService.findAllByAuthorId(2L, PageRequest.of(0, 1)).toList());
    }

    @Test
    void search() {
        //given
        Post post = new Post();
        post.setAuthor(new User());
        post.setId(2L);
        post.setContent("Lorem ipusm dolores sit ament");
        post.setCreationTime(LocalDateTime.now());
        post.setTitle("title");
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        //when
        when(postRepository.findAllByContentContainingOrTitleContaining(any(), any(), any())).thenReturn(new PageImpl<Post>(postList));
        //then
        assertEquals(postList, postService.search("", PageRequest.of(0, 1)).toList());
    }

    @Test
    void update() {
        //given
        Post post = new Post();
        post.setAuthor(new User());
        post.setId(2L);
        post.setContent("Lorem ipusm dolores sit ament");
        post.setCreationTime(LocalDateTime.now());
        post.setTitle("title");
        final Long existingId = 2L;
        final Long notExistingId = 3L;
        //when
        when(postRepository.saveAndFlush(any())).thenReturn(post);
        when(postRepository.existsById(existingId)).thenReturn(true);
        when(postRepository.existsById(notExistingId)).thenReturn(false);
        //then
        post.setId(existingId);
        assertEquals(post, postService.update(post));
        verify(postRepository, times(1)).saveAndFlush(post);
        post.setId(null);
        assertThrows(BadRequestException.class, () -> postService.update(post));
        post.setId(notExistingId);
        assertThrows(BadRequestException.class, () -> postService.update(post));
    }

    @Test
    void create() {
        //given
        Post post = new Post();
        post.setAuthor(new User());
        post.setId(2L);
        post.setContent("Lorem ipusm dolores sit ament");
        post.setCreationTime(LocalDateTime.now());
        post.setTitle("title");
        //when
        when(postRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(post));
        //then
        postService.create(post);
        verify(postRepository, times(1)).saveAndFlush(post);
    }

    @Test
    void delete() {
        //given
        Post post = new Post();
        post.setAuthor(new User());
        post.setId(2L);
        post.setContent("Lorem ipusm dolores sit ament");
        post.setCreationTime(LocalDateTime.now());
        post.setTitle("title");
        //then
        postService.delete(post);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void deleteById() {
        //then
        postService.deleteById(2L);
        verify(postRepository, times(1)).deleteById(2L);
    }

    @Test
    void findAll() {
        //given
        Post post = new Post();
        post.setAuthor(new User());
        post.setId(2L);
        post.setContent("Lorem ipusm dolores sit ament");
        post.setCreationTime(LocalDateTime.now());
        post.setTitle("title");
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        //when
        when(postRepository.findAll((Pageable) any())).thenReturn(new PageImpl<Post>(postList));
        //then
        assertEquals(postList, postService.findAll(PageRequest.of(0, 1)).toList());

    }
}