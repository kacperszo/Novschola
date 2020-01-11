package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.SchoolClass;
import io.novschola.repositories.SchoolClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class SchoolClassService {

    private SchoolClassRepository schoolClassRepository;

    public SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    public SchoolClass update(SchoolClass schoolClass) throws Exception{
       return null;
    }

    public SchoolClass create(SchoolClass schoolClass) throws Exception{
        return null;
    }

    public Iterable<SchoolClass> updateAll(Iterable<SchoolClass> classes) throws Exception{
        return null;
    }

    public SchoolClass findById(Long id) throws Exception {
        return null;
    }

    public SchoolClass findByName(String name) throws Exception {
        return null;
    }

    public Boolean existsById(Long id) {
        return null;
    }

    public Iterable<SchoolClass> findAll() {
        return null;
    }

    public Iterable<SchoolClass> findAllById(Iterable<Long> idList){
        return null;
    }

    public Long count() {
        return null;
    }

    public void deleteById(Long id){
    }

    public void delete(SchoolClass schoolClass) {
    }

    public void deleteAll(Iterable<SchoolClass> classes) {
    }

    public void deleteAll() {
    }



}
