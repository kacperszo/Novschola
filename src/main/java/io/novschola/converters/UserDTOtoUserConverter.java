package io.novschola.converters;

import io.novschola.api.v1.model.UserDTO;
import io.novschola.model.User;
import io.novschola.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserDTOtoUserConverter implements Converter<UserDTO, User> {
    private UserService userService;
    private SchoolClassDTOtoSchoolClassConverter schoolClassDTOtoSchoolClassConverter;

    public UserDTOtoUserConverter(UserService userService, SchoolClassDTOtoSchoolClassConverter schoolClassDTOtoSchoolClassConverter) {
        this.userService = userService;
        this.schoolClassDTOtoSchoolClassConverter = schoolClassDTOtoSchoolClassConverter;
    }


    @Override
    public User convert(UserDTO from) {
        User user = userService.findById(from.getId());
        user.setEmail(from.getEmail());
        user.setFirstName(from.getFirstName());
        user.setLastName(from.getLastName());
        user.setBio(from.getBio());
        user.setSchoolClass(schoolClassDTOtoSchoolClassConverter.convert(from.getSchoolClass()));
        return user;
    }
}
