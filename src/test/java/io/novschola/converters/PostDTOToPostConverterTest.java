package io.novschola.converters;

import io.novschola.api.v1.model.PostDTO;
import io.novschola.api.v1.model.SchoolClassDTO;
import io.novschola.api.v1.model.UserDTO;
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

class PostDTOToPostConverterTest {

    private final String content = "lotrem ipsum";
    private final LocalDateTime creationTime = LocalDateTime.now();
    private final String title = "title";
    private final Long id = 2L;
    private final String email = "adas@asd.com";
    private final String bio = "bio bio bio";
    private final String lastName = "John";
    private final String firstName = "Doo";

    PostDTOToPostConverter postDTOToPostConverter;
    UserDTOtoUserConverter userDTOtoUserConverter;
    SchoolClassDTOtoSchoolClassConverter schoolClassDTOtoSchoolClassConverter;
    UserDTO userDTO;
    PostDTO postDTO;
    User user;
    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        schoolClassDTOtoSchoolClassConverter = new SchoolClassDTOtoSchoolClassConverter();
        userDTOtoUserConverter = new UserDTOtoUserConverter(userService, schoolClassDTOtoSchoolClassConverter);
        postDTOToPostConverter = new PostDTOToPostConverter(userDTOtoUserConverter);
        userDTO = new UserDTO();
        postDTO = new PostDTO();
        postDTO.setAuthor(userDTO);
        postDTO.setContent(content);
        postDTO.setCreationTime(creationTime);
        postDTO.setTitle(title);
        postDTO.setId(id);
        userDTO.setSchoolClass(new SchoolClassDTO());
        user=new User();
        user.setSchoolClass(new SchoolClass());

    }

    @Test
    void convert() {
        when(userService.findById(any())).thenReturn(user);
        Post post = postDTOToPostConverter.convert(postDTO);
        assertEquals(user, post.getAuthor());
        assertEquals(id, post.getId());
        assertEquals(content, post.getContent());
        assertEquals(creationTime, post.getCreationTime());
        assertEquals(title, post.getTitle());

    }
}