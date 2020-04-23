package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.SchoolClassResponse;
import io.novschola.model.SchoolClass;
import org.springframework.stereotype.Component;

/**
 * Class responsible for converting SchoolClass objects to SchoolClassResponse objects
 *
 * @author Kacper Szot
 */
@Component
public class SchoolClassToSchoolClassResponseConverter implements Converter<SchoolClass, SchoolClassResponse> {
    @Override
    public SchoolClassResponse convert(SchoolClass from) {
        if (from == null) {
            return null;
        }
        return SchoolClassResponse.builder()
                .id(from.getId())
                .name(from.getName())
                .build();
    }
}
