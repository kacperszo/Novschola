package io.novschola.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.novschola.api.v1.model.dto.request.CreatePostRequest;
import io.novschola.api.v1.model.dto.request.UpdatePostRequest;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.Post;
import io.novschola.model.Role;
import io.novschola.model.User;
import io.novschola.repositories.RoleRepository;
import io.novschola.service.PostService;
import io.novschola.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PostService postService;
    @MockBean
    UserService userService;
    @MockBean
    RoleRepository roleRepository;

    User author;
    Post post;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        author = new User();
        author.setId(2L);
        author.setEmail("test@test.com");
        author.setLastName("");
        author.setFirstName("");
        author.setPassword("");
        post = new Post();
        post.setTitle("testTitle");
        post.setContent("testContent");
        post.setId(2L);
        post.setAuthor(author);
    }

    @Test
    void getPostsShouldReturn200OK() throws Exception {


        List<Post> postList = new ArrayList<>();
        postList.add(post);

        when(postService.findAll(any())).thenReturn(new PageImpl<Post>(postList));
        mockMvc.
                perform(get("/v1/posts"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(
                        content()
                                .string(containsString("testTitle"))
                );
    }

    @Test
    void getPostShouldReturn200OK() throws Exception {
        when(postService.findById(any())).thenReturn(post);
        mockMvc.
                perform(get("/v1/posts/1"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(
                        content()
                                .string(containsString("testTitle"))
                );
    }
    @Test
    void getPostShouldReturn404() throws Exception {
        when(postService.findById(any())).thenThrow(new ItemNotFoundException());
        mockMvc.
                perform(get("/v1/posts/1"))
                .andDo(print())
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    void searchPosts() throws Exception {
        List<Post> postList = new ArrayList<>();
        postList.add(post);

        when(postService.search(any(),any())).thenReturn(new PageImpl<Post>(postList));
        mockMvc.
                perform(get("/v1/posts/search/test"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(
                        content()
                                .string(containsString("testTitle"))
                );
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void createPostShouldReturnHttp201() throws Exception {

        when(userService.findByEmail(any())).thenReturn(author);
        when(postService.create(any())).thenReturn(post);
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setContent("test_content");
        createPostRequest.setTitle("test_title");

        mockMvc.perform(
                post("/v1/posts")
                        .content(asJsonString(createPostRequest))
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
    void createPostShouldReturnHttp400() throws Exception {
        mockMvc.perform(
                post("/v1/posts")
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
    void createPostShouldReturnHttp401() throws Exception {
        mockMvc.perform(
                post("/v1/posts")
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
    void updatePostShouldReturnHttp200() throws Exception {

        when(postService.update(any())).thenReturn(post);
        when(postService.findById(any())).thenReturn(post);
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setContent("test_content");
        updatePostRequest.setTitle("test_title");

        mockMvc.perform(
                put("/v1/posts/2")
                        .content(asJsonString(updatePostRequest))
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
    void updatePostShouldReturnHttp404() throws Exception {

        when(postService.update(any())).thenReturn(post);
        when(postService.findById(any())).thenThrow(new ItemNotFoundException());
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setContent("test_content");
        updatePostRequest.setTitle("test_title");

        mockMvc.perform(
                put("/v1/posts/2")
                        .content(asJsonString(updatePostRequest))
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
    void updatePostShouldReturnHttp400() throws Exception {
        mockMvc.perform(
                put("/v1/posts/2")
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
    void updatePostShouldReturnHttp401() throws Exception {

        when(postService.update(any())).thenReturn(post);
        when(postService.findById(any())).thenReturn(post);
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setContent("test_content");
        updatePostRequest.setTitle("test_title");

        mockMvc.perform(
                put("/v1/posts/2")
                        .content(asJsonString(post))
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
    void updatePostShouldReturnHttp403() throws Exception {

        when(postService.update(any())).thenReturn(post);
        when(postService.findById(any())).thenReturn(post);
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setContent("test_content");
        updatePostRequest.setTitle("test_title");

        mockMvc.perform(
                put("/v1/posts/2")
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
    @WithMockUser(username="admin",roles="ADMIN")
    void updatePostShouldReturnHttp200AdminAccess() throws Exception {

        when(postService.update(any())).thenReturn(post);
        when(postService.findById(any())).thenReturn(post);
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setContent("test_content");
        updatePostRequest.setTitle("test_title");

        mockMvc.perform(
                put("/v1/posts/2")
                        .content(asJsonString(post))
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
    @WithMockUser(username = "test@test.com")
    void deletePostShouldReturnHttp200() throws Exception {
        when(postService.findById(any())).thenReturn(post);
        when(roleRepository.findRoleByRole("ROLE_ADMIN")).thenReturn(new Role("ROLE_ADMIN"));

        mockMvc.perform(
                delete("/v1/posts/2")
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
    @WithMockUser(username = "test@test.com")
    void deletePostShouldReturnHttp404() throws Exception {
        when(postService.findById(any())).thenThrow( new ItemNotFoundException());
        when(roleRepository.findRoleByRole("ROLE_ADMIN")).thenReturn(new Role("ROLE_ADMIN"));
        mockMvc.perform(
                delete("/v1/posts/2")
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
    @WithMockUser(username = "bad@test.com")
    void deletePostShouldReturnHttp401() throws Exception {
        when(roleRepository.findRoleByRole("ROLE_ADMIN")).thenReturn(new Role("ROLE_ADMIN"));
        when(postService.findById(any())).thenReturn(post);

        mockMvc.perform(
                delete("/v1/posts/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(
                        status()
                                .isForbidden()
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