package io.novschola.converters;

import io.novschola.api.v1.model.SchoolClassDTO;
import io.novschola.model.SchoolClass;
import org.springframework.stereotype.Component;

@Component
public class SchoolClassToSchoolClassDTOConverter implements Converter<SchoolClass, SchoolClassDTO> {
    @Override
    public SchoolClassDTO convert(SchoolClass from) {
        SchoolClassDTO schoolClassDTO = new SchoolClassDTO();
        schoolClassDTO.setId(from.getId());
        schoolClassDTO.setName(from.getName());
        return schoolClassDTO;
    }
}
