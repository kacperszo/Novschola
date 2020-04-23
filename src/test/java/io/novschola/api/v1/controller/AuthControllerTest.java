package io.novschola.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.novschola.api.v1.model.dto.request.AuthRequest;
import io.novschola.model.User;
import io.novschola.service.JwtTokenService;
import io.novschola.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    UserService userService;
    @MockBean
    JwtTokenService jwtTokenService;
    Authentication authentication;
    @Autowired
    private MockMvc mockMvc;

    Authentication getAuthentication() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return "password";
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "user";
            }
        };
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        authentication = getAuthentication();

    }

    @Test
    void authWithCorrectCredentials() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setEmail("test@test.com");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userService.findByEmail(any())).thenReturn(user);
        when(jwtTokenService.generateToken(any())).thenReturn("VALID_TOKEN");

        this.mockMvc
                .perform(
                        post("/v1/auth")
                                .content(asJsonString(new AuthRequest()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )

                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("VALID_TOKEN")))
                .andExpect(content().string(containsString("user")));

    }

    @Test
    void authWithBadCredentials() throws Exception {
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("wrong password"));

        this.mockMvc
                .perform(
                        post("/v1/auth")
                                .content(asJsonString(new AuthRequest()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )

                .andDo(print()).andExpect(status().isUnauthorized());

    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}