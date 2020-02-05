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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SchoolClassServiceTest {

    final Long existingId = 2L;
    final Long notExistingId = 3L;
    final String name = "2c";
    final List<User> students = new ArrayList<>();


    @Mock
    SchoolClassRepository schoolClassRepository;
    SchoolClassService schoolClassService;
    SchoolClass schoolClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        schoolClassService = new SchoolClassService(schoolClassRepository);
        schoolClass = new SchoolClass();
        schoolClass.setId(existingId);
        schoolClass.setName(name);
        schoolClass.setStudents(students);
    }


    @Test
    void update() {
        when(schoolClassRepository.save(schoolClass)).thenReturn(schoolClass);
        when(schoolClassRepository.existsById(schoolClass.getId())).thenReturn(true);
        when(schoolClassRepository.existsById(notExistingId)).thenReturn(false);

        assertEquals(schoolClassService.update(schoolClass), schoolClass);
        SchoolClass newSchoolClass = new SchoolClass();
        newSchoolClass.setName(name);
        assertThrows(BadRequestException.class, () -> schoolClassService.update(newSchoolClass));
        SchoolClass notExistingSchoolClass = new SchoolClass();
        notExistingSchoolClass.setId(notExistingId);
        notExistingSchoolClass.setStudents(students);
        notExistingSchoolClass.setName(name);
        assertThrows(BadRequestException.class, () -> schoolClassService.update(notExistingSchoolClass));
    }

    @Test
    void create() {
        when(schoolClassRepository.save(schoolClass)).thenReturn(schoolClass);
        assertEquals(schoolClassService.create(schoolClass), schoolClass);
        verify(schoolClassRepository, times(1)).save(any());
    }

    @Test
    void findById() {
        when(schoolClassRepository.findById(existingId)).thenReturn(java.util.Optional.ofNullable(schoolClass));
        when(schoolClassRepository.findById(notExistingId)).thenReturn(java.util.Optional.empty());
        assertEquals(schoolClass, schoolClassService.findById(existingId));
        assertThrows(ItemNotFoundException.class, () -> schoolClassService.findById(notExistingId));
    }

    @Test
    void findByName() {
        when(schoolClassRepository.findByName(name)).thenReturn(java.util.Optional.ofNullable(schoolClass));
        when(schoolClassRepository.findByName("not existing name")).thenReturn(java.util.Optional.empty());
        assertEquals(schoolClass, schoolClassService.findByName(name));
        assertThrows(ItemNotFoundException.class, () -> schoolClassService.findByName("not existing name"));
    }

    @Test
    void findAll() {
        schoolClassService.findAll();
        verify(schoolClassRepository, times(1)).findAll();
    }

    @Test
    void findAllById() {
        schoolClassService.findAllById(new ArrayList<Long>());
        verify(schoolClassRepository, times(1)).findAllById(any());
    }

    @Test
    void deleteById() {
        schoolClassService.deleteById(existingId);
        verify(schoolClassRepository, times(1)).deleteById(any());
    }

    @Test
    void delete() {
        schoolClassService.delete(schoolClass);
        verify(schoolClassRepository, times(1)).delete(any());
    }
}