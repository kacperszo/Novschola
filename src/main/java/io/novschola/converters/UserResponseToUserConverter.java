package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.UserResponse;
import io.novschola.model.User;
import io.novschola.service.UserService;
import org.springframework.stereotype.Component;

/**
 * Class responsible for converting UserResponse objects to User objects
 *
 * @author Kacper Szot
 */
@Component
public class UserResponseToUserConverter implements Converter<UserResponse, User> {
    private UserService userService;
    private SchoolClassResponseToSchoolClassConverter schoolClassResponseToSchoolClassConverter;

    public UserResponseToUserConverter(UserService userService, SchoolClassResponseToSchoolClassConverter schoolClassResponseToSchoolClassConverter) {
        this.userService = userService;
        this.schoolClassResponseToSchoolClassConverter = schoolClassResponseToSchoolClassConverter;
    }


    @Override
    public User convert(UserResponse from) {
        if (from == null) {
            return null;
        }
        User user = userService.findById(from.getId());
        user.setEmail(from.getEmail());
        user.setFirstName(from.getFirstName());
        user.setLastName(from.getLastName());
        user.setBio(from.getBio());
        user.setSchoolClass(schoolClassResponseToSchoolClassConverter.convert(from.getSchoolClass()));
        return user;
    }
}
