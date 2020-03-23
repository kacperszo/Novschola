package io.novschola.service;

import io.novschola.model.Comment;
import io.novschola.model.Post;
import io.novschola.model.User;
import io.novschola.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceTest {
    @Mock
    CommentRepository commentRepository;
    CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        commentService = new CommentService(commentRepository);
    }

    @Test
    void create() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";

        final User author = User.builder().
                id(id)
                .build();

        final Post post = Post.builder()
                .id(id)
                .author(author)
                .build();

        final Comment comment = Comment.builder()
                .id(null)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(null)
                .build();
        //when
        when(commentRepository.save(any())).thenReturn(Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(LocalDateTime.now())
                .build());
        //then
        Comment newComment = commentService.create(comment);
        verify(commentRepository, times(1)).save(comment);
        assertEquals(newComment.getContent(), comment.getContent());
        assertEquals(newComment.getAuthor(), comment.getAuthor());
        assertEquals(newComment.getPost(), comment.getPost());

        assertNotNull(newComment.getId());
        assertNotNull(newComment.getCreationTime());
    }

    @Test
    void update() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByAuthorId() {
    }

    @Test
    void findByAuthor() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findAllByPost() {
    }

    @Test
    void findAllByPostId() {
    }

    @Test
    void findAllByAuthor() {
    }

    @Test
    void findAllByAuthorId() {
    }

    @Test
    void delete() {

    }

    @Test
    void deleteById() {

    }
}