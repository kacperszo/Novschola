package io.novschola.converters;

import io.novschola.api.v1.model.UserDTO;
import io.novschola.model.User;
import org.springframework.stereotype.Component;
/**
 * Class responsible for converting User objects to UserDTO objects
 * @author Kacper Szot
 */
@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {
    private SchoolClassToSchoolClassDTOConverter converter;

    public UserToUserDTOConverter(SchoolClassToSchoolClassDTOConverter converter) {
        this.converter = converter;
    }

    @Override
    public UserDTO convert(User from) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(from.getId());
        userDTO.setEmail(from.getEmail());
        userDTO.setFirstName(from.getFirstName());
        userDTO.setLastName(from.getLastName());
        userDTO.setBio(from.getBio());
        userDTO.setSchoolClass(converter.convert(from.getSchoolClass()));
        return userDTO;
    }
}
