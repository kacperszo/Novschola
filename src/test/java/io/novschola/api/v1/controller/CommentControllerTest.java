package io.novschola.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.novschola.api.v1.model.dto.request.CreateCommentRequest;
import io.novschola.api.v1.model.dto.request.UpdateCommentRequest;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.Comment;
import io.novschola.model.Post;
import io.novschola.model.Role;
import io.novschola.model.User;
import io.novschola.repositories.RoleRepository;
import io.novschola.service.CommentService;
import io.novschola.service.PostService;
import io.novschola.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private PostService postService;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void createCommentShouldReturnHttp201() throws Exception {
        //given
        User user = User.builder()
                .id(3L)
                .firstName("John")
                .lastName("Doo")
                .active(true)
                .email("test@test.com")
                .build();
        Post post = Post.builder()
                .id(2L)
                .author(user)
                .title("post title")
                .content("post content")
                .build();
        Comment comment = Comment.builder()
                .author(user)
                .post(post)
                .id(1L)
                .content("test content")
                .build();


        CreateCommentRequest createCommentRequest = new CreateCommentRequest();
        createCommentRequest.setContent("content");

        //when
        when(userService.findByEmail("test@test.com")).thenReturn(user);
        when(postService.findById(2L)).thenReturn(post);
        when(commentService.create(any())).thenReturn(comment);

        //then
        mockMvc.perform(
                post("/v1/posts/2")
                        .content(asJsonString(createCommentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isCreated()
                );
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void createCommentShouldReturnHttp404() throws Exception {
        //given
        User user = User.builder()
                .id(3L)
                .firstName("John")
                .lastName("Doo")
                .active(true)
                .email("test@test.com")
                .build();


        CreateCommentRequest createCommentRequest = new CreateCommentRequest();
        createCommentRequest.setContent("content");

        //when
        when(userService.findByEmail("test@test.com")).thenReturn(user);
        when(postService.findById(2L)).thenThrow(ItemNotFoundException.class);
        //then
        mockMvc.perform(
                post("/v1/posts/2")
                        .content(asJsonString(createCommentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isNotFound()
                );
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void createCommentShouldReturnHttp400() throws Exception {
        mockMvc.perform(
                post("/v1/posts/2")
                        .content(asJsonString("{}"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isBadRequest()
                );
    }

    @Test
    void createCommentShouldReturnHttp401() throws Exception {
        mockMvc.perform(
                post("/v1/posts/2")
                        .content(asJsonString("{}"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isUnauthorized()
                );
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void updateCommentShouldReturnHttp200() throws Exception {

        //given
        User user = User.builder()
                .id(3L)
                .firstName("John")
                .lastName("Doo")
                .active(true)
                .email("test@test.com")
                .build();
        Post post = Post.builder()
                .id(2L)
                .author(user)
                .title("post title")
                .content("post content")
                .build();
        Comment comment = Comment.builder()
                .author(user)
                .post(post)
                .id(1L)
                .content("test content")
                .build();


        UpdateCommentRequest commentRequest = new UpdateCommentRequest();
        commentRequest.setContent("content");

        //when
        when(userService.findByEmail("test@test.com")).thenReturn(user);
        when(commentService.findById(1L)).thenReturn(comment);
        when(commentService.update(any())).thenReturn(comment);

        //then

        mockMvc.perform(
                put("/v1/comments/1")
                        .content(asJsonString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isOk()
                ).andExpect(
                content()
                        .string(containsString("test@test.com")));
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void updateCommentShouldReturnHttp404() throws Exception {

        //given
        User user = User.builder()
                .id(3L)
                .firstName("John")
                .lastName("Doo")
                .active(true)
                .email("test@test.com")
                .build();
        Post post = Post.builder()
                .id(2L)
                .author(user)
                .title("post title")
                .content("post content")
                .build();
        Comment comment = Comment.builder()
                .author(user)
                .post(post)
                .id(1L)
                .content("test content")
                .build();


        UpdateCommentRequest commentRequest = new UpdateCommentRequest();
        commentRequest.setContent("content");

        //when

        when(userService.findByEmail("test@test.com")).thenReturn(user);
        when(commentService.findById(2L)).thenThrow(ItemNotFoundException.class);
        when(commentService.update(any())).thenReturn(comment);

        //then

        mockMvc.perform(
                put("/v1/comments/2")
                        .content(asJsonString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isNotFound()
                );
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void updateCommentShouldReturnHttp400() throws Exception {
        mockMvc.perform(
                put("/v1/comments/2")
                        .content(asJsonString("{}"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isBadRequest()
                );
    }

    @Test
    void updateCommentShouldReturnHttp401() throws Exception {

        //given
        User user = User.builder()
                .id(3L)
                .firstName("John")
                .lastName("Doo")
                .active(true)
                .email("test@test.com")
                .build();
        Post post = Post.builder()
                .id(2L)
                .author(user)
                .title("post title")
                .content("post content")
                .build();
        Comment comment = Comment.builder()
                .author(user)
                .post(post)
                .id(1L)
                .content("test content")
                .build();


        UpdateCommentRequest commentRequest = new UpdateCommentRequest();
        commentRequest.setContent("content");

        //when

        when(userService.findByEmail("test@test.com")).thenReturn(user);
        when(commentService.findById(2L)).thenReturn(comment);
        when(commentService.update(any())).thenReturn(comment);

        mockMvc.perform(
                put("/v1/comments/2")
                        .content(asJsonString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isUnauthorized()
                );
    }

    @Test
    @WithMockUser(username = "bad@test.com")
    void updateCommentShouldReturnHttp403() throws Exception {
        //given
        User user = User.builder()
                .id(3L)
                .firstName("John")
                .lastName("Doo")
                .active(true)
                .email("test@test.com")
                .build();
        Post post = Post.builder()
                .id(2L)
                .author(user)
                .title("post title")
                .content("post content")
                .build();
        Comment comment = Comment.builder()
                .author(user)
                .post(post)
                .id(1L)
                .content("test content")
                .build();


        UpdateCommentRequest commentRequest = new UpdateCommentRequest();
        commentRequest.setContent("content");

        //when

        when(userService.findByEmail("test@test.com")).thenReturn(user);
        when(commentService.findById(2L)).thenReturn(comment);
        when(commentService.update(any())).thenReturn(comment);

        mockMvc.perform(
                put("/v1/comments/2")
                        .content(asJsonString(post))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isForbidden()
                );
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateCommentShouldReturnHttp200AdminAccess() throws Exception {
        //given
        User user = User.builder()
                .id(3L)
                .firstName("John")
                .lastName("Doo")
                .active(true)
                .email("test@test.com")
                .build();
        Post post = Post.builder()
                .id(2L)
                .author(user)
                .title("post title")
                .content("post content")
                .build();
        Comment comment = Comment.builder()
                .author(user)
                .post(post)
                .id(1L)
                .content("test content")
                .build();


        UpdateCommentRequest commentRequest = new UpdateCommentRequest();
        commentRequest.setContent("content");

        //when

        when(userService.findByEmail("test@test.com")).thenReturn(user);
        when(commentService.findById(2L)).thenReturn(comment);
        when(commentService.update(any())).thenReturn(comment);

        mockMvc.perform(
                put("/v1/comments/2")
                        .content(asJsonString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isOk()
                );
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteCommentShouldReturnHttp200AdminAccess() throws Exception {
        //given
        User user = User.builder()
                .id(3L)
                .firstName("John")
                .lastName("Doo")
                .active(true)
                .email("test@test.com")
                .build();
        Post post = Post.builder()
                .id(2L)
                .author(user)
                .title("post title")
                .content("post content")
                .build();
        Comment comment = Comment.builder()
                .author(user)
                .post(post)
                .id(1L)
                .content("test content")
                .build();
        //when
        when(commentService.findById(2L)).thenReturn(comment);
        when(roleRepository.findRoleByRole("ROLE_ADMIN")).thenReturn(new Role("ROLE_ADMIN"));

        //then

        mockMvc.perform(
                delete("/v1/comments/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isOk()
                );
    }

    @Test
    @WithMockUser(username = "bad@test.com")
    void deleteCommentShouldReturnHttp403() throws Exception {
        //given
        User user = User.builder()
                .id(3L)
                .firstName("John")
                .lastName("Doo")
                .active(true)
                .email("test@test.com")
                .build();
        Post post = Post.builder()
                .id(2L)
                .author(user)
                .title("post title")
                .content("post content")
                .build();
        Comment comment = Comment.builder()
                .author(user)
                .post(post)
                .id(1L)
                .content("test content")
                .build();


        UpdateCommentRequest commentRequest = new UpdateCommentRequest();
        commentRequest.setContent("content");

        //when

        when(userService.findByEmail("test@test.com")).thenReturn(user);
        when(commentService.findById(2L)).thenReturn(comment);

        mockMvc.perform(
                delete("/v1/comments/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isForbidden()
                );
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void deleteCommentShouldReturnHttp200() throws Exception {

        //given
        User user = User.builder()
                .id(3L)
                .firstName("John")
                .lastName("Doo")
                .active(true)
                .email("test@test.com")
                .build();
        Post post = Post.builder()
                .id(2L)
                .author(user)
                .title("post title")
                .content("post content")
                .build();
        Comment comment = Comment.builder()
                .author(user)
                .post(post)
                .id(1L)
                .content("test content")
                .build();


        UpdateCommentRequest commentRequest = new UpdateCommentRequest();
        commentRequest.setContent("content");

        //when
        when(userService.findByEmail("test@test.com")).thenReturn(user);
        when(commentService.findById(1L)).thenReturn(comment);

        //then

        mockMvc.perform(
                delete("/v1/comments/1")
                        .content(asJsonString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isOk()
                );
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}