package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.Comment;
import io.novschola.model.Post;
import io.novschola.model.User;
import io.novschola.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    void create_correct_successful() {
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
        when(commentRepository.existsById(any())).thenReturn(true);
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
    void update_correct_successful() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";
        final LocalDateTime creationTime = LocalDateTime.now();

        final User author = User.builder().
                id(id)
                .build();

        final Post post = Post.builder()
                .id(id)
                .author(author)
                .build();

        final Comment comment = Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(creationTime)
                .build();
        //when
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentRepository.existsById(any())).thenReturn(true);
        //then
        Comment newComment = commentService.update(comment);
        verify(commentRepository, times(1)).save(comment);
        assertEquals(newComment, comment);
    }

    @Test
    void update_nullId_fail() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";
        final LocalDateTime creationTime = LocalDateTime.now();

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
                .creationTime(creationTime)
                .build();
        //when
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentRepository.existsById(any())).thenReturn(true);
        //then
        assertThrows(BadRequestException.class, () -> {
            commentService.update(comment);
        });
    }

    @Test
    void update_notExistingComment_fail() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";
        final LocalDateTime creationTime = LocalDateTime.now();

        final User author = User.builder().
                id(id)
                .build();

        final Post post = Post.builder()
                .id(id)
                .author(author)
                .build();

        final Comment comment = Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(creationTime)
                .build();
        //when
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentRepository.existsById(any())).thenReturn(false);
        //then
        assertThrows(BadRequestException.class, () -> {
            commentService.update(comment);
        });
    }

    @Test
    void findById_correct_successful() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";
        final LocalDateTime creationTime = LocalDateTime.now();

        final User author = User.builder().
                id(id)
                .build();

        final Post post = Post.builder()
                .id(id)
                .author(author)
                .build();

        final Comment comment = Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(creationTime)
                .build();
        //when
        when(commentRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(comment));
        //then
        Comment newComment = commentService.findById(id);
        assertEquals(newComment, comment);
    }

    @Test
    void findById_notExistingComment_fail() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";
        final LocalDateTime creationTime = LocalDateTime.now();

        final User author = User.builder().
                id(id)
                .build();

        final Post post = Post.builder()
                .id(id)
                .author(author)
                .build();

        final Comment comment = Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(creationTime)
                .build();
        //when
        when(commentRepository.findById(any())).thenReturn(java.util.Optional.empty());
        //then
        assertThrows(ItemNotFoundException.class, () -> {
            commentService.findById(id);
        });
    }

    @Test
    void findByAuthorId_correct_successful() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";
        final LocalDateTime creationTime = LocalDateTime.now();

        final User author = User.builder().
                id(id)
                .build();

        final Post post = Post.builder()
                .id(id)
                .author(author)
                .build();

        final Comment comment = Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(creationTime)
                .build();

        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(comment);
        //when
        when(commentRepository.findAllByAuthorId(any())).thenReturn(comments);
        //then
        assertEquals(commentService.findAllByAuthorId(id).get(0), comments.get(0));
    }

    @Test
    void findAll_correct_successful() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";
        final LocalDateTime creationTime = LocalDateTime.now();

        final User author = User.builder().
                id(id)
                .build();

        final Post post = Post.builder()
                .id(id)
                .author(author)
                .build();

        final Comment comment = Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(creationTime)
                .build();

        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(comment);
        //when
        when(commentRepository.findAll()).thenReturn(comments);
        //then
        assertEquals(commentService.findAll().get(0), comments.get(0));
    }

    @Test
    void findAllByPost_correct_successful() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";
        final LocalDateTime creationTime = LocalDateTime.now();

        final User author = User.builder().
                id(id)
                .build();

        final Post post = Post.builder()
                .id(id)
                .author(author)
                .build();

        final Comment comment = Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(creationTime)
                .build();

        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(comment);
        //when
        when(commentRepository.findAllByPost(post)).thenReturn(comments);
        //then
        assertEquals(commentService.findAllByPost(post).get(0), comments.get(0));
    }

    @Test
    void findAllByPostId_correct_successful() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";
        final LocalDateTime creationTime = LocalDateTime.now();

        final User author = User.builder().
                id(id)
                .build();

        final Post post = Post.builder()
                .id(id)
                .author(author)
                .build();

        final Comment comment = Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(creationTime)
                .build();

        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(comment);
        //when
        when(commentRepository.findAllByPostId(id)).thenReturn(comments);
        //then
        assertEquals(commentService.findAllByPostId(id).get(0), comments.get(0));
    }

    @Test
    void findAllByAuthor_correct_successful() {
        //given
        final Long id = 3L;
        final String content = "Lorem ipusm";
        final LocalDateTime creationTime = LocalDateTime.now();

        final User author = User.builder().
                id(id)
                .build();

        final Post post = Post.builder()
                .id(id)
                .author(author)
                .build();

        final Comment comment = Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .author(author)
                .creationTime(creationTime)
                .build();

        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(comment);
        //when
        when(commentRepository.findAllByAuthor(author)).thenReturn(comments);
        //then
        assertEquals(commentService.findAllByAuthor(author).get(0), comments.get(0));
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