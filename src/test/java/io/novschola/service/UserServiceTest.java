package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.User;
import io.novschola.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private static final String EMAIL = "test@test.com";
    private static final String NAME = "John";
    private static final String LASTNAME = "Doo";
    private static final String PASSWORD = "PASSWORD";
    @Mock
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    UserService userService;
    User user;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, bCryptPasswordEncoder);
        user = new User();
        user.setEmail(EMAIL);
        user.setFirstName(NAME);
        user.setLastName(LASTNAME);
        user.setPassword(PASSWORD);
    }

    @Test
    void create() {
        when(userRepository.save(any())).thenReturn(user);
        User newUSer = userService.create(this.user);
        verify(userRepository, times(1)).save(any());
        Assert.notNull(newUSer.getActivationKey(), "activation key is null");
        assertThat(newUSer.getPassword(), not(equalTo(PASSWORD)));
    }

    @Test
    void updateTest() throws Exception{
        final String NEWLASTNAME = "New Last Name";
        final Long ID = 2L;
        when(userRepository.save(any())).thenReturn(user);
        user.setId(ID);
        user.setLastName(NEWLASTNAME);
        user = userService.update(this.user);
        verify(userRepository, times(1)).save(user);
        assertEquals(user.getLastName(), NEWLASTNAME);
        try {
            user.setId(null);
            userService.update(user);
        } catch (BadRequestException e) {
            Assertions.assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

}