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

import java.util.ArrayList;
import java.util.Optional;

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
    void update() throws Exception {
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

    @Test
    void findById() throws Exception {
        final Long EXISTINGID = 2L;
        final Long NOTEXISTINGID = 3L;
        when(userRepository.findById(EXISTINGID)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findById(NOTEXISTINGID)).thenReturn(java.util.Optional.empty());

        User foundUser = userService.findById(EXISTINGID);
        assertEquals(foundUser, user);

        try {
            userService.findById(NOTEXISTINGID);
        } catch (ItemNotFoundException e) {
            Assertions.assertThat(e).isInstanceOf(ItemNotFoundException.class);
        }

    }

    @Test
    void findByEmail() {
        final String EXISTINGEMAIL = "emial@ema.com";
        final String NOTEXISTINGEMAIL = "loler@mail.com";
        when(userRepository.findByEmail(EXISTINGEMAIL)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findByEmail(NOTEXISTINGEMAIL)).thenReturn(Optional.empty());

        User foundUser = userService.findByEmail(EXISTINGEMAIL);
        assertEquals(foundUser, user);
        try {
            userService.findByEmail(NOTEXISTINGEMAIL);
        } catch (ItemNotFoundException e) {
            Assertions.assertThat(e).isInstanceOf(ItemNotFoundException.class);
        }
    }

    @Test
    void getAllActive() {
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAllByActiveTrue()).thenReturn(users);
        assertEquals(userService.getAllActive().get(0), users.get(0));
        verify(userRepository, times(1)).findAllByActiveTrue();
    }

    @Test
    void findByFirstName() {
        final String EXISTINGNAME = "Joul";
        final String NOTEXISTINGENAME = "Poul";
        when(userRepository.findByFirstName(EXISTINGNAME)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findByFirstName(NOTEXISTINGENAME)).thenReturn(Optional.empty());

        User foundUser = userService.findByFirstName(EXISTINGNAME);
        assertEquals(foundUser, user);
        try {
            userService.findByFirstName(NOTEXISTINGENAME);
        } catch (ItemNotFoundException e) {
            Assertions.assertThat(e).isInstanceOf(ItemNotFoundException.class);
        }
    }

    @Test
    void findByLastName() {
        final String EXISTINGNAME = "Joul";
        final String NOTEXISTINGENAME = "Poul";
        when(userRepository.findByLastName(EXISTINGNAME)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findByLastName(NOTEXISTINGENAME)).thenReturn(Optional.empty());

        User foundUser = userService.findByLastName(EXISTINGNAME);
        assertEquals(foundUser, user);
        try {
            userService.findByLastName(NOTEXISTINGENAME);
        } catch (ItemNotFoundException e) {
            Assertions.assertThat(e).isInstanceOf(ItemNotFoundException.class);
        }
    }

    @Test
    void findByActivationKey() {
        final String EXISTINGKEY = "Rj341321312@11231234%!#1231@33122344553112";
        final String NOTEXISTINGEKEY = "Bj341321312@11231234%!#1231@33122344553112";
        when(userRepository.findByActivationKey(EXISTINGKEY)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findByActivationKey(NOTEXISTINGEKEY)).thenReturn(Optional.empty());

        User foundUser = userService.findByActivationKey(EXISTINGKEY);
        assertEquals(foundUser, user);
        try {
            userService.findByActivationKey(NOTEXISTINGEKEY);
        } catch (ItemNotFoundException e) {
            Assertions.assertThat(e).isInstanceOf(ItemNotFoundException.class);
        }
    }

    @Test
    void activate() {
    }
}