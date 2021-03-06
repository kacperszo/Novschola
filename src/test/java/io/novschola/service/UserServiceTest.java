package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.Role;
import io.novschola.model.User;
import io.novschola.repositories.RoleRepository;
import io.novschola.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private final String email = "test@test.com";
    private final String name = "John";
    private final String lastName = "Doo";
    private final String password = "PASSWORD";

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    JavaMailSender javaMailSender;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    UserService userService;
    User user;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, roleRepository, bCryptPasswordEncoder, javaMailSender,"http://127.0.0.1:8080",false);
        user = new User();
        user.setEmail(email);
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setPassword(password);
    }

    @Test
    void create() throws MessagingException {
        when(userRepository.saveAndFlush(any())).thenReturn(user);
        when(roleRepository.findRoleByRole(any())).thenReturn(new Role("USER_ROLE"));
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));
        User newUSer = userService.create(this.user);
        verify(userRepository, times(1)).saveAndFlush(any());
        Assert.notNull(newUSer.getActivationKey(), "activation key is null");
        assertThat(newUSer.getPassword(), not(equalTo(password)));
    }

    @Test
    void update() throws Exception {
        final String newLastName = "New Last Name";
        final Long existingId = 2L;
        final Long notExistingId = 3L;
        when(userRepository.saveAndFlush(any())).thenReturn(user);
        when(userRepository.existsById(existingId)).thenReturn(true);
        when(userRepository.existsById(notExistingId)).thenReturn(false);
        user.setId(existingId);
        user.setLastName(newLastName);
        user = userService.update(this.user);
        verify(userRepository, times(1)).saveAndFlush(user);
        assertEquals(user.getLastName(), newLastName);
        user.setId(null);
        assertThrows(BadRequestException.class, () -> userService.update(user));
        user.setId(notExistingId);
        assertThrows(BadRequestException.class, () -> userService.update(user));
    }

    @Test
    void findById() throws Exception {
        final Long existingId = 2L;
        final Long notExistingId = 3L;
        when(userRepository.findById(existingId)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findById(notExistingId)).thenReturn(java.util.Optional.empty());
        User foundUser = userService.findById(existingId);
        assertEquals(foundUser, user);
        assertThrows(ItemNotFoundException.class, () -> userService.findById(notExistingId));

    }

    @Test
    void findByEmail() {
        final String existingEmail = "emial@ema.com";
        final String notExistingEmail = "loler@mail.com";
        when(userRepository.findByEmail(existingEmail)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findByEmail(notExistingEmail)).thenReturn(Optional.empty());
        User foundUser = userService.findByEmail(existingEmail);
        assertEquals(foundUser, user);
        assertThrows(ItemNotFoundException.class, () -> userService.findByEmail(notExistingEmail));
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
        final String existingName = "Joul";
        final String notExistingName = "Poul";
        when(userRepository.findByFirstName(existingName)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findByFirstName(notExistingName)).thenReturn(Optional.empty());

        User foundUser = userService.findByFirstName(existingName);
        assertEquals(foundUser, user);
        assertThrows(ItemNotFoundException.class, () -> userService.findByFirstName(notExistingName));
    }

    @Test
    void findByLastName() {
        final String existingName = "Joul";
        final String notExistingName = "Poul";
        when(userRepository.findByLastName(existingName)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findByLastName(notExistingName)).thenReturn(Optional.empty());

        User foundUser = userService.findByLastName(existingName);
        assertEquals(foundUser, user);
        assertThrows(ItemNotFoundException.class, () -> userService.findByLastName(notExistingName));
    }

    @Test
    void findByActivationKey() {
        final String existingKey = "Rj341321312@11231234%!#1231@33122344553112";
        final String notExistingKey = "Bj341321312@11231234%!#1231@33122344553112";
        when(userRepository.findByActivationKey(existingKey)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findByActivationKey(notExistingKey)).thenReturn(Optional.empty());

        User foundUser = userService.findByActivationKey(existingKey);
        assertEquals(foundUser, user);
        assertThrows(ItemNotFoundException.class, () -> userService.findByActivationKey(notExistingKey));
    }

    @Test
    void activate() {
        final Long existingId = 2L;
        final Long notExistingId = 3L;
        final String existingKey = "Rj341321312@11231234%!#1231@33122344553112";
        final String notExistingKey = "Bj341321312@11231234%!#1231@33122344553112";

        when(userRepository.findByActivationKey(existingKey)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findByActivationKey(notExistingKey)).thenReturn(Optional.empty());
        when(userRepository.existsById(existingId)).thenReturn(true);
        when(userRepository.existsById(notExistingId)).thenReturn(false);
        when(userRepository.saveAndFlush(any())).thenReturn(user);

        user.setId(existingId);
        User foundUser = userService.activate(existingKey);
        assertEquals(foundUser, user);
        assertThrows(ItemNotFoundException.class, () -> userService.activate(notExistingKey));
        user.setId(notExistingId);
        assertThrows(BadRequestException.class, () -> userService.activate(existingKey));
    }

    @Test
    void findAll() {
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(userService.findAll().get(0), users.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void existsByEmail() {
        String existingEmail = "arara@ra.com";
        String notExistingEmail = "lalalla@cadas.com";
        when(userRepository.existsByEmail(existingEmail)).thenReturn(true);
        when(userRepository.existsByEmail(notExistingEmail)).thenReturn(false);

        assertTrue(userService.existsByEmail(existingEmail));
        assertFalse(userService.existsByEmail(notExistingEmail));
    }

    @Test
    void delete() {
        userService.delete(user);
        verify(userRepository, times(1)).delete(user);
    }
}