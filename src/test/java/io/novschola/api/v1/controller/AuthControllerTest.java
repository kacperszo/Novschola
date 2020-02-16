package io.novschola.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.novschola.api.v1.model.JwtRequest;
import io.novschola.model.User;
import io.novschola.service.JwtTokenService;
import io.novschola.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
class AuthControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    UserService userService;
    @MockBean
    JwtTokenService jwtTokenService;
    @Autowired
    private MockMvc mockMvc;
    private String email = "email@email.com";
    private String password = "password";
    private AuthController authController;

    @BeforeEach
    void setUp() {
        when(userService.findByEmail(any())).thenReturn(new User());
        when(jwtTokenService.generateToken(any())).thenReturn("token");

    }

    @Test
    void auth() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setEmail(email);
        jwtRequest.setPassword(password);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(jwtRequest);

        mockMvc.perform(post("/v1/auth").contentType(APPLICATION_JSON_UTF8).content(request))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("token")));
        mockMvc.perform(post("/v1/auth")).andExpect(status().isBadRequest());

    }
}