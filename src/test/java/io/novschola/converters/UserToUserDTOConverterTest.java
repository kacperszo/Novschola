package io.novschola.converters;

import io.novschola.api.v1.model.UserDTO;
import io.novschola.model.SchoolClass;
import io.novschola.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserToUserDTOConverterTest {

    @Test
    void convert() {
        UserToUserDTOConverter converter = new UserToUserDTOConverter();
        User user = new User();
        Long id = 2L;
        LocalDateTime date = LocalDateTime.now();
        boolean active = true;
        String lastName = "John";
        String firstName = "Doo";
        String key = "asdasdasd1qe21ed31rt13341";
        List<String> roles = new ArrayList<>();
        SchoolClass clazz = new SchoolClass();
        clazz.setId(id);
        clazz.setName("2c");
        String email = "adas@asd.com";
        String password = "Password";
        String bio = "bio bio bio";
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
        UserDTO userDTO = converter.convert(user);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getBio(), userDTO.getBio());
        assertEquals(user.getSchoolClass().getId(), userDTO.getSchoolClassId());
    }
}