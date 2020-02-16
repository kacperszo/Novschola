package io.novschola.service;

import io.jsonwebtoken.security.Keys;
import io.novschola.exception.BadJwtTokenException;
import io.novschola.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceTest {


    final String JWTSecret = "12345678912345769284739048727739578883462847630936957489629043343434345376123947192939327674519031900000000000000000001310000003200002312";
    final Long id = 2L;
    final String firstName = "John";
    final String lastName = "Doo";
    final String password = "password";
    final String email = "email@email.com";
    JwtTokenService jwtTokenService;

    @BeforeEach
    void setUp() {
        jwtTokenService = new JwtTokenService(JWTSecret);
    }

    @Test
    void testToken() {
        User user = new User();

        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);

        String token = jwtTokenService.generateToken(user);

        //test
        assertNotNull(token);

        assertEquals(jwtTokenService.getEmailFromToken(token),email);
        assertEquals(jwtTokenService.getEmailFromToken(token),email);
        assertNotNull(jwtTokenService.getExpirationDateFromToken(token));
        assertEquals(jwtTokenService.getKey(), Keys.hmacShaKeyFor(JWTSecret.getBytes()));

        assertThrows(BadJwtTokenException.class, ()->{
           jwtTokenService.getAllClaimsFromToken("bad token");
        });

        assertFalse(jwtTokenService.isTokenExpired(token));
        assertTrue(jwtTokenService.validateToken(token, new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        }));
    }

}