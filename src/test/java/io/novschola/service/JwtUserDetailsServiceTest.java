package io.novschola.service;

import io.novschola.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class JwtUserDetailsServiceTest {

    final Long id = 2L;
    final String firstName = "John";
    final String lastName = "Doo";
    final String password = "password";
    final String email = "email@email.com";
    @Mock
    UserService userService;
    JwtUserDetailsService jwtUserDetailsService;
    User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        jwtUserDetailsService = new JwtUserDetailsService(userService);
    }


    @Test
    void loadUserByUsername() {
        when(userService.findByEmail(any())).thenReturn(user);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
        assertEquals(userDetails.getUsername(),user.getEmail());
        assertEquals(userDetails.getAuthorities(),user.getRoles());
        assertEquals(userDetails.getPassword(),user.getPassword());
        assertEquals(userDetails.isEnabled(),user.isActive());
    }
}