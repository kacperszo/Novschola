package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.SchoolClass;
import io.novschola.model.User;
import io.novschola.repositories.SchoolClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SchoolClassServiceTest {

    //constants
    final Long EXISTINGID = 2L;
    final Long NONEXISTINGID = 3L;
    final String NAME = "2c";
    final List<User> STUDENTS = new ArrayList<>();


    @Mock
    SchoolClassRepository schoolClassRepository;
    SchoolClassService schoolClassService;
    SchoolClass schoolClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        schoolClassService = new SchoolClassService(schoolClassRepository);
        schoolClass = new SchoolClass();
        schoolClass.setId(EXISTINGID);
        schoolClass.setName(NAME);
        schoolClass.setStudents(STUDENTS);
    }


    @Test
    void update() throws Exception {
        when(schoolClassRepository.save(schoolClass)).thenReturn(schoolClass);
        when(schoolClassRepository.existsById(schoolClass.getId())).thenReturn(true);
        when(schoolClassRepository.existsById(NONEXISTINGID)).thenReturn(false);

        assertEquals(schoolClassService.update(schoolClass), schoolClass);
        SchoolClass newSchoolClass = new SchoolClass();
        newSchoolClass.setName(NAME);
        newSchoolClass.setStudents(STUDENTS);
        try {
            schoolClassService.update(newSchoolClass);
        } catch (BadRequestException e) {
            assertThat(e)
                    .isInstanceOf(BadRequestException.class);
        }
        SchoolClass notExistingSchoolClass = new SchoolClass();
        notExistingSchoolClass.setId(NONEXISTINGID);
        notExistingSchoolClass.setStudents(STUDENTS);
        notExistingSchoolClass.setName(NAME);
        try {
            schoolClassService.update(notExistingSchoolClass);
        } catch (ItemNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(ItemNotFoundException.class);
        }
    }

    @Test
    void create() {
    }

    @Test
    void updateAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void existsById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findAllById() {
    }

    @Test
    void count() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void testDeleteAll() {
    }
}