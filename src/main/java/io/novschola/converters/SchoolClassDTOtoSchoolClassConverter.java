package io.novschola.converters;

import io.novschola.api.v1.model.SchoolClassDTO;
import io.novschola.model.SchoolClass;
import org.springframework.stereotype.Component;

@Component
public class SchoolClassDTOtoSchoolClass implements Converter<SchoolClassDTO, SchoolClass> {
    @Override
    public SchoolClass convert(SchoolClassDTO from) {
        return null;
    }
}
