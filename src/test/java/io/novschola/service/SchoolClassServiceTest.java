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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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
    void create() throws Exception {
        schoolClass.setId(null);
        when(schoolClassRepository.save(schoolClass)).thenReturn(schoolClass);
        assertEquals(schoolClassService.create(schoolClass), schoolClass);
        SchoolClass existingSchoolClass = new SchoolClass();
        existingSchoolClass.setId(EXISTINGID);
        existingSchoolClass.setName(NAME);
        existingSchoolClass.setStudents(STUDENTS);
        try {
            schoolClassService.create(existingSchoolClass);
        } catch (BadRequestException e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    void updateAll() throws Exception {
        when(schoolClassRepository.save(schoolClass)).thenReturn(schoolClass);
        when(schoolClassRepository.existsById(schoolClass.getId())).thenReturn(true);
        when(schoolClassRepository.existsById(NONEXISTINGID)).thenReturn(false);

        List<SchoolClass> schoolClasses = new ArrayList<>();
        SchoolClass notExistingSchoolClass = new SchoolClass();
        notExistingSchoolClass.setId(NONEXISTINGID);
        notExistingSchoolClass.setName(NAME);
        notExistingSchoolClass.setStudents(STUDENTS);

        schoolClasses.add(schoolClass);
        schoolClasses.add(schoolClass);
        schoolClassService.updateAll(schoolClasses);
        verify(schoolClassRepository, times(1)).saveAll(schoolClasses);

        schoolClasses.add(notExistingSchoolClass);

        try {
            assertEquals(schoolClasses, schoolClassService.updateAll(schoolClasses));
        } catch (BadRequestException e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }


    }

    @Test
    void findById() throws Exception {
        when(schoolClassRepository.findById(EXISTINGID)).thenReturn(java.util.Optional.ofNullable(schoolClass));
        when(schoolClassRepository.findById(NONEXISTINGID)).thenReturn(java.util.Optional.empty());
        assertEquals(schoolClass, schoolClassService.findById(EXISTINGID));
        try {
            schoolClassService.findById(NONEXISTINGID);
        } catch (ItemNotFoundException e) {
            assertThat(e).isInstanceOf(ItemNotFoundException.class);
        }
    }

    @Test
    void findByName() throws Exception {
        when(schoolClassRepository.findByName(NAME)).thenReturn(java.util.Optional.ofNullable(schoolClass));
        when(schoolClassRepository.findByName("NOTEXISTINGNAME")).thenReturn(java.util.Optional.empty());
        assertEquals(schoolClass, schoolClassService.findByName(NAME));
        try {
            schoolClassService.findByName("NOTEXISTINGNAME");
        } catch (ItemNotFoundException e) {
            assertThat(e).isInstanceOf(ItemNotFoundException.class);
        }
    }

    @Test
    void existsById() {
        when(schoolClassRepository.existsById(any())).thenReturn(true);
        assertTrue(schoolClassService.existsById(EXISTINGID));
        verify(schoolClassRepository, times(1)).existsById(EXISTINGID);
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