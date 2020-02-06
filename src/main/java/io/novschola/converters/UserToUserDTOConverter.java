package io.novschola.converters;

import io.novschola.api.v1.model.UserDTO;
import io.novschola.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User from) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(from.getId());
        userDTO.setEmail(from.getEmail());
        userDTO.setFirstName(from.getFirstName());
        userDTO.setLastName(from.getLastName());
        userDTO.setBio(from.getBio());
        userDTO.setSchoolClassId(from.getSchoolClass().getId());
        return userDTO;
    }
}
