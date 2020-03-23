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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CommentToCommentResponseConverterTest {

    @Mock
    UserToUserResponseConverter userToUserResponseConverter;
    @Mock
    PostToPostResponseConverter postToPostResponseConverter;

    CommentToCommentResponseConverter commentToCommentResponseConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        commentToCommentResponseConverter = new CommentToCommentResponseConverter(userToUserResponseConverter, postToPostResponseConverter);
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
        final Comment comment = new Comment();

        user.setId(id);
        userResponse.setId(id);
        post.setId(id);
        postResponse.setId(id);
        post.setAuthor(user);
        postResponse.setAuthor(userResponse);
        comment.setAuthor(user);
        comment.setId(id);
        comment.setContent(content);
        comment.setCreationTime(creationTime);
        comment.setPost(post);

        //when

        when(userToUserResponseConverter.convert(any())).thenReturn(userResponse);
        when(postToPostResponseConverter.convert(any())).thenReturn(postResponse);

        //then

        CommentResponse commentResponse = commentToCommentResponseConverter.convert(comment);
        assertEquals(commentResponse.getId(), id);
        assertEquals(commentResponse.getAuthor(), userResponse);
        assertEquals(commentResponse.getContent(), content);
        assertEquals(commentResponse.getCreationTime(), creationTime);
        assertEquals(commentResponse.getPost(), postResponse);
    }
}