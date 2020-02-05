package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.SchoolClass;
import io.novschola.repositories.SchoolClassRepository;
import org.springframework.stereotype.Service;

@Service
public class SchoolClassService {

    private SchoolClassRepository schoolClassRepository;

    public SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    public SchoolClass update(SchoolClass schoolClass){
        if (schoolClass.getId() == null || !schoolClassRepository.existsById(schoolClass.getId())) {
            throw new BadRequestException();
        }
        return schoolClassRepository.save(schoolClass);
    }

    public SchoolClass create(SchoolClass schoolClass){
        schoolClass.setId(null);
        return schoolClassRepository.save(schoolClass);
    }

    public SchoolClass findById(Long id) {
        return schoolClassRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public SchoolClass findByName(String name) {
        return schoolClassRepository.findByName(name).orElseThrow(ItemNotFoundException::new);
    }


    public Iterable<SchoolClass> findAll() {
        return schoolClassRepository.findAll();
    }

    public Iterable<SchoolClass> findAllById(Iterable<Long> idList) {
        return schoolClassRepository.findAllById(idList);
    }

    public void deleteById(Long id) {
        schoolClassRepository.deleteById(id);
    }

    public void delete(SchoolClass schoolClass) {
        schoolClassRepository.delete(schoolClass);
    }

}
