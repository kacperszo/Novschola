package io.novschola.converters;

import io.novschola.api.v1.model.dto.response.SchoolClassResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SchoolClassResponseToSchoolClassConverterTest {
    final Long ID = 2L;
    final String NAME = "1c";
    SchoolClassResponse schoolClassResponse;
    SchoolClassResponseToSchoolClassConverter converter;

    @BeforeEach
    void setUp() {
        schoolClassResponse = new SchoolClassResponse();
        schoolClassResponse.setId(ID);
        schoolClassResponse.setName(NAME);
        converter = new SchoolClassResponseToSchoolClassConverter();
    }

    @Test
    void convert() throws Exception{
        assertEquals(schoolClassResponse.getId(),converter.convert(schoolClassResponse).getId());
        assertEquals(schoolClassResponse.getName(),converter.convert(schoolClassResponse).getName());
    }
}