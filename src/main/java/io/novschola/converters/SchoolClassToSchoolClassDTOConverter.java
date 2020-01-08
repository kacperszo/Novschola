package io.novschola.converters;

import io.novschola.api.v1.model.SchoolClassDTO;
import io.novschola.model.SchoolClass;
import org.springframework.stereotype.Component;

@Component
public class SchoolClassToSchoolClassDTO implements Converter<SchoolClass, SchoolClassDTO> {
    @Override
    public SchoolClassDTO convert(SchoolClass from) {
        return null;
    }
}
