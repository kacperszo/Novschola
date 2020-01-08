package io.novschola.converters;

import io.novschola.model.SchoolClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SchoolClassToSchoolClassDTOConverterTest {
    final Long ID = 2L;
    final String NAME = "1c";
    SchoolClass schoolClass;
    SchoolClassToSchoolClassDTOConverter converter;

    @BeforeEach
    void setUp() {
        schoolClass = new SchoolClass();
        converter = new SchoolClassToSchoolClassDTOConverter();
        schoolClass.setId(ID);
        schoolClass.setName(NAME);
    }

    @Test
    void convert() throws Exception {
        assertEquals(schoolClass.getId(), converter.convert(schoolClass).getId());
        assertEquals(schoolClass.getName(), converter.convert(schoolClass).getName());
    }
}