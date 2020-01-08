package io.novschola.converters;

import io.novschola.api.v1.model.SchoolClassDTO;
import io.novschola.model.SchoolClass;
import org.springframework.stereotype.Component;

@Component
public class SchoolClassDTOtoSchoolClassConverter implements Converter<SchoolClassDTO, SchoolClass> {
    @Override
    public SchoolClass convert(SchoolClassDTO from) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName(from.getName());
        schoolClass.setId(from.getId());
        return schoolClass;
    }
}
