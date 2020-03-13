package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.SchoolClassResponse;
import io.novschola.api.v1.model.dto.response.UserResponse;
import io.novschola.model.Role;
import io.novschola.model.SchoolClass;
import io.novschola.model.User;
import io.novschola.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserResponseToUserConverterTest {

    SchoolClassResponseToSchoolClassConverter schoolClassResponseToSchoolClassConverter;
    UserResponseToUserConverter userResponseToUserConverter;
    User user;
    UserResponse userResponse;
    Long id = 2L;
    LocalDateTime date = LocalDateTime.now();
    boolean active = true;
    String lastName = "John";
    String firstName = "Doo";
    String key = "asdasdasd1qe21ed31rt13341";
    List<Role> roles = new ArrayList<>();
    SchoolClass clazz = new SchoolClass();
    SchoolClassResponse clazzDTO = new SchoolClassResponse();
    String email = "adas@asd.com";
    String password = "Password";
    String bio = "bio bio bio";
    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        schoolClassResponseToSchoolClassConverter = new SchoolClassResponseToSchoolClassConverter();
        MockitoAnnotations.initMocks(this);
        userResponseToUserConverter = new UserResponseToUserConverter(userService, schoolClassResponseToSchoolClassConverter);
        user = new User();
        userResponse = new UserResponse();
        user.setId(id);
        userResponse.setId(id);
        user.setCreateDate(date);
        user.setActive(active);
        user.setLastName(lastName);
        userResponse.setLastName(lastName);
        user.setFirstName(firstName);
        userResponse.setFirstName(firstName);
        user.setActivationKey(key);
        user.setRoles(roles);
        user.setSchoolClass(clazz);
        userResponse.setSchoolClass(clazzDTO);
        user.setEmail(email);
        userResponse.setEmail(email);
        user.setPassword(password);
        user.setBio(bio);
        userResponse.setBio(bio);
        clazz.setId(id);
        clazz.setName("2c");
        clazzDTO.setId(id);
        clazzDTO.setName("2c");
    }

    @Test
    void convert() {
        when(userService.findById(id)).thenReturn(user);
        userResponse.setFirstName("new first name");
        User convertedUser = userResponseToUserConverter.convert(userResponse);
        assertEquals(user.getId(), convertedUser.getId());
        assertEquals(user.getEmail(), convertedUser.getEmail());
        assertEquals("new first name", convertedUser.getFirstName());
        assertEquals(user.getLastName(), convertedUser.getLastName());
        assertEquals(user.getBio(), convertedUser.getBio());
        assertEquals(user.getSchoolClass(), convertedUser.getSchoolClass());
        assertEquals(user.getPassword(), convertedUser.getPassword());
        assertEquals(user.getActivationKey(), convertedUser.getActivationKey());
        assertEquals(user.isActive(), convertedUser.isActive());
        assertEquals(user.getCreateDate(), convertedUser.getCreateDate());
        assertEquals(user.getRoles(), convertedUser.getRoles());
    }
}