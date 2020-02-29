package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.UserResponse;
import io.novschola.model.Role;
import io.novschola.model.SchoolClass;
import io.novschola.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserToUserResponseConverterTest {
    SchoolClassResponseToSchoolClassConverter schoolClassResponseToSchoolClassConverter = new SchoolClassResponseToSchoolClassConverter();
    SchoolClassToSchoolClassResponseConverter schoolClassToSchoolClassResponseConverter = new SchoolClassToSchoolClassResponseConverter();
    UserToUserResponseConverter converter = new UserToUserResponseConverter(schoolClassToSchoolClassResponseConverter);
    User user = new User();
    Long id = 2L;
    LocalDateTime date = LocalDateTime.now();
    boolean active = true;
    String lastName = "John";
    String firstName = "Doo";
    String key = "asdasdasd1qe21ed31rt13341";
    List<Role> roles = new ArrayList<>();
    SchoolClass clazz = new SchoolClass();
    String email = "adas@asd.com";
    String password = "Password";
    String bio = "bio bio bio";
    UserResponse userResponse;

    @BeforeEach
    void setUp(){
        clazz.setId(id);
        clazz.setName("2c");
        user.setId(id);
        user.setCreateDate(date);
        user.setActive(active);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setActivationKey(key);
        user.setRoles(roles);
        user.setSchoolClass(clazz);
        user.setEmail(email);
        user.setPassword(password);
        user.setBio(bio);

    }

    @Test
    void convert() {
        userResponse = converter.convert(user);
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(user.getFirstName(), userResponse.getFirstName());
        assertEquals(user.getLastName(), userResponse.getLastName());
        assertEquals(user.getBio(), userResponse.getBio());
        assertEquals(user.getSchoolClass(), schoolClassResponseToSchoolClassConverter.convert(userResponse.getSchoolClass()));
    }
}