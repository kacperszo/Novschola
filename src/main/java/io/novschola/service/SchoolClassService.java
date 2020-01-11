package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.SchoolClass;
import io.novschola.repositories.SchoolClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SchoolClassService {

    private SchoolClassRepository schoolClassRepository;

    public SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    public SchoolClass update(SchoolClass schoolClass) throws Exception {
        if (schoolClass.getId() == null) {
            throw new BadRequestException();
        }
        if (!schoolClassRepository.existsById(schoolClass.getId())) {
            throw new ItemNotFoundException();
        }
        return schoolClassRepository.save(schoolClass);
    }

    public SchoolClass create(SchoolClass schoolClass) throws Exception {
        if (schoolClass.getId() != null) {
            throw new BadRequestException();
        }
        return schoolClassRepository.save(schoolClass);
    }

    public Iterable<SchoolClass> updateAll(Iterable<SchoolClass> classes) throws Exception {
        classes.forEach(schoolClass -> {
            if (schoolClass.getId() == null || !schoolClassRepository.existsById(schoolClass.getId())) {
                throw new BadRequestException();
            }
        });
        return schoolClassRepository.saveAll(classes);
    }

    public SchoolClass findById(Long id) throws Exception {
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(id);
        if (schoolClass.isPresent()){
            return schoolClass.get();
        }
        throw new ItemNotFoundException();
    }

    public SchoolClass findByName(String name) throws Exception {
        Optional<SchoolClass> schoolClass = schoolClassRepository.findByName(name);
        if (schoolClass.isPresent()){
            return schoolClass.get();
        }
        throw new ItemNotFoundException();
    }

    public Boolean existsById(Long id) {
        return schoolClassRepository.existsById(id);
    }

    public Iterable<SchoolClass> findAll() {
        return schoolClassRepository.findAll();
    }

    public Iterable<SchoolClass> findAllById(Iterable<Long> idList) {
        return schoolClassRepository.findAllById(idList);
    }

    public Long count() {
        return schoolClassRepository.count();
    }

    public void deleteById(Long id) {
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(id);
        schoolClass.ifPresent(aClass -> schoolClassRepository.delete(aClass));
    }

    public void delete(SchoolClass schoolClass) {
        schoolClassRepository.delete(schoolClass);
    }

    public void deleteAll(Iterable<SchoolClass> classes) {
        classes.forEach(schoolClass -> {
            if (schoolClass.getId() == null || !schoolClassRepository.existsById(schoolClass.getId())) {
                throw new BadRequestException();
            }
        });
        schoolClassRepository.deleteAll(classes);
    }

    public void deleteAll() {
        schoolClassRepository.deleteAll();
    }


}