package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.CommentResponse;
import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.api.v1.model.dto.response.UserResponse;
import io.novschola.model.Comment;
import io.novschola.model.Post;
import io.novschola.model.User;
import io.novschola.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CommentResponseToCommentConverterTest {

    @Mock
    UserResponseToUserConverter userResponseToUserConverter;
    @Mock
    CommentService commentService;

    CommentResponseToCommentConverter commentResponseToCommentConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        commentResponseToCommentConverter = new CommentResponseToCommentConverter(userResponseToUserConverter, commentService);
    }

    @Test
    void convert() {

        //given

        final Long id = 3L;
        final String content = "content";
        final LocalDateTime creationTime = LocalDateTime.now();
        final UserResponse userResponse = new UserResponse();
        final PostResponse postResponse = new PostResponse();
        final User user = new User();
        final Post post = new Post();
        final CommentResponse commentResponse = new CommentResponse();

        final Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(user);
        comment.setCreationTime(creationTime);
        comment.setId(id);
        comment.setPost(post);
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(comment);
        user.setId(id);
        userResponse.setId(id);
        post.setId(id);
        postResponse.setId(id);

        commentResponse.setAuthor(userResponse);
        commentResponse.setId(id);
        commentResponse.setContent(content);
        commentResponse.setCreationTime(creationTime);

        //when

        when(commentService.findById(any())).thenReturn(comment);
        when(userResponseToUserConverter.convert(any())).thenReturn(user);

        //then

        Comment newComment = commentResponseToCommentConverter.convert(commentResponse);
        assertEquals(newComment.getAuthor(), user);
        assertEquals(newComment.getContent(), content);
        assertEquals(newComment.getId(), id);
        assertEquals(newComment.getPost(), post);
        assertEquals(newComment.getCreationTime(), creationTime);

    }
}