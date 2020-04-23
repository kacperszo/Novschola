package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.CommentResponse;
import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.model.Comment;
import io.novschola.model.Post;
import io.novschola.model.SchoolClass;
import io.novschola.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PostToPostResponseConverterTest {

    final Long id = 2L;
    final LocalDateTime creationTime = LocalDateTime.now();
    final String title = "title";
    final String content = "Lorem ipsum";
    final String name = "Lorem ipsum";
    PostToPostResponseConverter postToPostResponseConverter;
    UserToUserResponseConverter userToUserResponseConverter;
    SchoolClassToSchoolClassResponseConverter schoolClassToSchoolClassResponseConverter;
    Post post;
    User author;
    SchoolClass schoolClass;
    @Mock
    CommentToCommentResponseConverter commentToCommentResponseConverter;
    Comment comment;
    CommentResponse commentResponse;
    ArrayList<Comment> comments;
    ArrayList<CommentResponse> commentResponses;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        schoolClassToSchoolClassResponseConverter = new SchoolClassToSchoolClassResponseConverter();
        userToUserResponseConverter = new UserToUserResponseConverter(schoolClassToSchoolClassResponseConverter);
        postToPostResponseConverter = new PostToPostResponseConverter(userToUserResponseConverter, commentToCommentResponseConverter);

        schoolClass = new SchoolClass();
        schoolClass.setId(id);
        schoolClass.setName(name);
        schoolClass.setStudents(new ArrayList<>());

        author = new User();
        author.setSchoolClass(schoolClass);
        author.setId(id);

        post = new Post();
        post.setId(id);
        post.setCreationTime(creationTime);
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);

        comment = new Comment();
        comment.setId(id);
        commentResponse = new CommentResponse();
        commentResponse.setId(id);
        comments = new ArrayList<>();
        commentResponses = new ArrayList<>();
        commentResponses.add(commentResponse);
        comments.add(comment);
        post.setComments(comments);
    }

    @Test
    void convert() {
        when(commentToCommentResponseConverter.convert(any())).thenReturn(commentResponse);
        PostResponse postResponse = postToPostResponseConverter.convert(post);
        assertEquals(postResponse.getId(), post.getId());
        assertEquals(postResponse.getAuthor(), userToUserResponseConverter.convert(post.getAuthor()));
        assertEquals(postResponse.getContent(), post.getContent());
        assertEquals(postResponse.getCreationTime(), post.getCreationTime());
        assertEquals(postResponse.getTitle(), post.getTitle());
        assertEquals(postResponse.getComments(),commentResponses);
    }
}