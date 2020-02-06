package io.novschola.converters;

import io.novschola.api.v1.model.UserDTO;
import io.novschola.model.User;
import io.novschola.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserDTOtoUserConverter implements Converter<UserDTO, User> {
    private UserService userService;

    public UserDTOtoUserConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User convert(UserDTO from) {
        return null;
    }
}
