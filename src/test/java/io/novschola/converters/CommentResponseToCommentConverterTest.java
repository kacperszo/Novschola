package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.CommentResponse;
import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.api.v1.model.dto.response.UserResponse;
import io.novschola.model.Comment;
import io.novschola.model.Post;
import io.novschola.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CommentResponseToCommentConverterTest {

    @Mock
    PostResponseToPostConverter postResponseToPostConverter;
    @Mock
    UserResponseToUserConverter userResponseToUserConverter;

    CommentResponseToCommentConverter commentResponseToCommentConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        commentResponseToCommentConverter = new CommentResponseToCommentConverter(userResponseToUserConverter, postResponseToPostConverter);
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

        user.setId(id);
        userResponse.setId(id);
        post.setId(id);
        postResponse.setId(id);

        commentResponse.setAuthor(userResponse);
        commentResponse.setId(id);
        commentResponse.setContent(content);
        commentResponse.setCreationTime(creationTime);
        commentResponse.setPost(postResponse);

        //when

        when(postResponseToPostConverter.convert(any())).thenReturn(post);
        when(userResponseToUserConverter.convert(any())).thenReturn(user);

        //then

        Comment comment = commentResponseToCommentConverter.convert(commentResponse);
        assertEquals(comment.getAuthor(), user);
        assertEquals(comment.getContent(), content);
        assertEquals(comment.getId(), id);
        assertEquals(comment.getPost(), post);
        assertEquals(comment.getCreationTime(), creationTime);

    }
}