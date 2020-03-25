package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.UserResponse;
import io.novschola.model.User;
import org.springframework.stereotype.Component;

/**
 * Class responsible for converting User objects to UserResponse objects
 *
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
        if (from == null) {
            return null;
        }
        return UserResponse.builder()
                .id(from.getId())
                .email(from.getEmail())
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .bio(from.getBio())
                .schoolClass(converter.convert(from.getSchoolClass()))
                .build();
    }
}
