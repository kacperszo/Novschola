package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.UserResponse;
import io.novschola.model.User;
import org.springframework.stereotype.Component;
/**
 * Class responsible for converting User objects to UserResponse objects
 * @author Kacper Szot
 */
@Component
public class UserToUserResponseConverter implements Converter<User, UserResponse> {
    private SchoolClassToSchoolClassResponseConverter converter;

    public UserToUserResponseConverter(SchoolClassToSchoolClassResponseConverter converter) {
        this.converter = converter;
    }

    @Override
    public UserResponse convert(User from) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(from.getId());
        userResponse.setEmail(from.getEmail());
        userResponse.setFirstName(from.getFirstName());
        userResponse.setLastName(from.getLastName());
        userResponse.setBio(from.getBio());
        userResponse.setSchoolClass(converter.convert(from.getSchoolClass()));
        return userResponse;
    }
}
