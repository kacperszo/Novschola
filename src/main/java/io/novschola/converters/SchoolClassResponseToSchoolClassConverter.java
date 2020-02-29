package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.SchoolClassResponse;
import io.novschola.model.SchoolClass;
import org.springframework.stereotype.Component;
/**
 * Class responsible for converting SchoolClassResponse objects to SchoolClass objects
 * @author Kacper Szot
 */
@Component
public class SchoolClassResponseToSchoolClassConverter implements Converter<SchoolClassResponse, SchoolClass> {
    @Override
    public SchoolClass convert(SchoolClassResponse from) {
        if (from==null){
            return null;
        }
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName(from.getName());
        schoolClass.setId(from.getId());
        return schoolClass;
    }
}
