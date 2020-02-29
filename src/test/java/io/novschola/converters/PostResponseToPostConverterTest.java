package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.api.v1.model.dto.response.SchoolClassResponse;
import io.novschola.api.v1.model.dto.response.UserResponse;
import io.novschola.model.Post;
import io.novschola.model.SchoolClass;
import io.novschola.model.User;
import io.novschola.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PostResponseToPostConverterTest {

    private final String content = "lotrem ipsum";
    private final LocalDateTime creationTime = LocalDateTime.now();
    private final String title = "title";
    private final Long id = 2L;
    private final String email = "adas@asd.com";
    private final String bio = "bio bio bio";
    private final String lastName = "John";
    private final String firstName = "Doo";

    PostResponseToPostConverter postResponseToPostConverter;
    UserResponseToUserConverter userResponseToUserConverter;
    SchoolClassResponseToSchoolClassConverter schoolClassResponseToSchoolClassConverter;
    UserResponse userResponse;
    PostResponse postResponse;
    User user;
    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        schoolClassResponseToSchoolClassConverter = new SchoolClassResponseToSchoolClassConverter();
        userResponseToUserConverter = new UserResponseToUserConverter(userService, schoolClassResponseToSchoolClassConverter);
        postResponseToPostConverter = new PostResponseToPostConverter(userResponseToUserConverter);
        userResponse = new UserResponse();
        postResponse = new PostResponse();
        postResponse.setAuthor(userResponse);
        postResponse.setContent(content);
        postResponse.setCreationTime(creationTime);
        postResponse.setTitle(title);
        postResponse.setId(id);
        userResponse.setSchoolClass(new SchoolClassResponse());
        user=new User();
        user.setSchoolClass(new SchoolClass());

    }

    @Test
    void convert() {
        when(userService.findById(any())).thenReturn(user);
        Post post = postResponseToPostConverter.convert(postResponse);
        assertEquals(user, post.getAuthor());
        assertEquals(id, post.getId());
        assertEquals(content, post.getContent());
        assertEquals(creationTime, post.getCreationTime());
        assertEquals(title, post.getTitle());

    }
}