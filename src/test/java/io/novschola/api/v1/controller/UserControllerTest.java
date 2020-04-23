package io.novschola.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.novschola.api.v1.model.dto.request.CreateUserRequest;
import io.novschola.api.v1.model.dto.request.UpdateUserRequest;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.Role;
import io.novschola.model.User;
import io.novschola.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUsers() throws Exception {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(2L);
        user.setEmail("test@test.com");
        users.add(user);

        when(userService.getAllActive()).thenReturn(users);
        mockMvc.
                perform(get("/v1/users"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(
                        content()
                                .string(containsString("test@test.com"))
                );
    }

    @Test
    void getUserUsingIdShouldReturnHttp200() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setEmail("test@test.com");
        when(userService.findActiveById(2L)).thenReturn(user);
        mockMvc.
                perform(get("/v1/users/2"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(
                        content()
                                .string(containsString("test@test.com"))
                );
    }

    @Test
    void getUserUsingIdShouldReturnHttp404() throws Exception {
        when(userService.findActiveById(3L)).thenThrow(new ItemNotFoundException());
        mockMvc.
                perform(get("/v1/users/3"))
                .andDo(print())
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    void createUserShouldReturnHttp201() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setEmail("test@test.com");
        when(userService.create(any())).thenReturn(user);
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@test.com");
        createUserRequest.setFirstName("firstname");
        createUserRequest.setLastName("lastname");
        createUserRequest.setPassword("Password00@");

        mockMvc.perform(
                post("/v1/users")
                        .content(asJsonString(createUserRequest))
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
    void createUserShouldReturnHttp400() throws Exception {


        mockMvc.perform(
                post("/v1/users")
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
    @WithMockUser(username = "test@test.com")
    void updateUserShouldReturnHttp200() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setEmail("test@test.com");
        when(userService.update(any())).thenReturn(user);
        when(userService.findById(2L)).thenReturn(user);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        updateUserRequest.setFirstName("firstname");
        updateUserRequest.setLastName("lastname");
        updateUserRequest.setBio("bio");


        mockMvc.perform(
                put("/v1/users/2")
                        .content(asJsonString(updateUserRequest))
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
    void updateUserShouldReturnHttp400() throws Exception {


        mockMvc.perform(
                put("/v1/users/2")
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
    @WithMockUser(username = "test2@test.com")
    void updateUserShouldReturnHttp403() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setEmail("test@test.com");
        when(userService.update(any())).thenReturn(user);
        when(userService.findById(2L)).thenReturn(user);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        updateUserRequest.setFirstName("firstname");
        updateUserRequest.setLastName("lastname");
        updateUserRequest.setBio("bio");


        mockMvc.perform(
                put("/v1/users/2")
                        .content(asJsonString(updateUserRequest))
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
    void activateUserShouldReturn200OK() throws Exception {
        when(userService.activate(any())).thenReturn(new User());

        mockMvc.perform(get("/v1/users/activate/1039123091293013/"))
                .andDo(print())
                .andExpect(status()
                        .isOk());
        verify(userService, times(1)).activate(any());
    }

    @Test
    void activateUserShouldReturn404() throws Exception {
        when(userService.activate(any())).thenThrow(new ItemNotFoundException());

        mockMvc.perform(get("/v1/users/activate/1039123091293013"))
                .andDo(print())
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    void updateUserShouldReturnHttp401() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setEmail("test@test.com");
        when(userService.update(any())).thenReturn(user);
        when(userService.findById(2L)).thenReturn(user);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        updateUserRequest.setFirstName("firstname");
        updateUserRequest.setLastName("lastname");
        updateUserRequest.setBio("bio");


        mockMvc.perform(
                put("/v1/users/2")
                        .content(asJsonString(updateUserRequest))
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
    void deletePostShouldReturnHttp200() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setEmail("test@test.com");
        when(userService.findById(any())).thenReturn(user);

        mockMvc.perform(
                delete("/v1/users/2")
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