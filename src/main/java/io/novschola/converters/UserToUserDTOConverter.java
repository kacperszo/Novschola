package io.novschola.converters;

import io.novschola.api.v1.model.UserDTO;
import io.novschola.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User from) {
        return null;
    }
}
