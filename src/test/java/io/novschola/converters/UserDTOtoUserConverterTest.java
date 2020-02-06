package io.novschola.converters;

import io.novschola.api.v1.model.SchoolClassDTO;
import io.novschola.api.v1.model.UserDTO;
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

class UserDTOtoUserConverterTest {

    SchoolClassDTOtoSchoolClassConverter schoolClassDTOtoSchoolClassConverter;
    UserDTOtoUserConverter userDTOtoUserConverter;
    User user;
    UserDTO userDTO;
    Long id = 2L;
    LocalDateTime date = LocalDateTime.now();
    boolean active = true;
    String lastName = "John";
    String firstName = "Doo";
    String key = "asdasdasd1qe21ed31rt13341";
    List<String> roles = new ArrayList<>();
    SchoolClass clazz = new SchoolClass();
    SchoolClassDTO clazzDTO = new SchoolClassDTO();
    String email = "adas@asd.com";
    String password = "Password";
    String bio = "bio bio bio";
    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        schoolClassDTOtoSchoolClassConverter = new SchoolClassDTOtoSchoolClassConverter();
        MockitoAnnotations.initMocks(this);
        user = new User();
        userDTO = new UserDTO();
        user.setId(id);
        userDTO.setId(id);
        user.setCreateDate(date);
        user.setActive(active);
        user.setLastName(lastName);
        userDTO.setLastName(lastName);
        user.setFirstName(firstName);
        userDTO.setFirstName(firstName);
        user.setActivationKey(key);
        user.setRoles(roles);
        user.setSchoolClass(clazz);
        userDTO.setSchoolClass(clazzDTO);
        user.setEmail(email);
        userDTO.setEmail(email);
        user.setPassword(password);
        user.setBio(bio);
        userDTO.setBio(bio);
        clazz.setId(id);
        clazz.setName("2c");
        clazzDTO.setId(id);
        clazzDTO.setName("2c");
    }

    @Test
    void convert() {
        when(userService.findById(id)).thenReturn(user);
        userDTO.setFirstName("new first name");
        User convertedUser = userDTOtoUserConverter.convert(userDTO);
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