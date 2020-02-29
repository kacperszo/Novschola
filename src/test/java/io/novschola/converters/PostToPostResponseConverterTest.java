package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.model.Post;
import io.novschola.model.SchoolClass;
import io.novschola.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @BeforeEach
    void setUp() {
        schoolClassToSchoolClassResponseConverter = new SchoolClassToSchoolClassResponseConverter();
        userToUserResponseConverter = new UserToUserResponseConverter(schoolClassToSchoolClassResponseConverter);
        postToPostResponseConverter = new PostToPostResponseConverter(userToUserResponseConverter);

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
        PostResponse postResponse = postToPostResponseConverter.convert(post);
        assertEquals(postResponse.getId(), post.getId());
        assertEquals(postResponse.getAuthor(), userToUserResponseConverter.convert(post.getAuthor()));
        assertEquals(postResponse.getContent(), post.getContent());
        assertEquals(postResponse.getCreationTime(), post.getCreationTime());
        assertEquals(postResponse.getTitle(), post.getTitle());
    }
}