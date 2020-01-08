package io.novschola.converters;

import io.novschola.api.v1.model.SchoolClassDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SchoolClassDTOtoSchoolClassConverterTest {
    final Long ID = 2L;
    final String NAME = "1c";
    SchoolClassDTO schoolClassDTO;
    SchoolClassDTOtoSchoolClassConverter converter;

    @BeforeEach
    void setUp() {
        schoolClassDTO = new SchoolClassDTO();
        schoolClassDTO.setId(ID);
        schoolClassDTO.setName(NAME);
        converter = new SchoolClassDTOtoSchoolClassConverter();
    }

    @Test
    void convert() throws Exception{
        assertEquals(schoolClassDTO.getId(),converter.convert(schoolClassDTO).getId());
        assertEquals(schoolClassDTO.getName(),converter.convert(schoolClassDTO).getName());
    }
}