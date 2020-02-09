package io.novschola.converters;

import io.novschola.api.v1.model.PostDTO;
import io.novschola.model.Post;
import io.novschola.model.SchoolClass;
import io.novschola.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostToPostDTOConverterTest {

    final Long id = 2L;
    final LocalDateTime creationTime = LocalDateTime.now();
    final String title = "title";
    final String content = "Lorem ipsum";
    final String name = "Lorem ipsum";
    PostToPostDTOConverter postToPostDTOConverter;
    UserToUserDTOConverter userToUserDTOConverter;
    SchoolClassToSchoolClassDTOConverter schoolClassToSchoolClassDTOConverter;
    Post post;
    User author;
    SchoolClass schoolClass;

    @BeforeEach
    void setUp() {
        schoolClassToSchoolClassDTOConverter = new SchoolClassToSchoolClassDTOConverter();
        userToUserDTOConverter = new UserToUserDTOConverter(schoolClassToSchoolClassDTOConverter);
        postToPostDTOConverter = new PostToPostDTOConverter(userToUserDTOConverter);

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
    }

    @Test
    void convert() {
        PostDTO postDTO = postToPostDTOConverter.convert(post);
        assertEquals(postDTO.getId(), post.getId());
        assertEquals(postDTO.getAuthor(), userToUserDTOConverter.convert(post.getAuthor()));
        assertEquals(postDTO.getContent(), post.getContent());
        assertEquals(postDTO.getCreationTime(), post.getCreationTime());
        assertEquals(postDTO.getTitle(), post.getTitle());
    }
}