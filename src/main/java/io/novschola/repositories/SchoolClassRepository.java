package io.novschola.repositories;

import io.novschola.model.SchoolClass;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SchoolClassRepository extends CrudRepository<SchoolClass, Long> {
    Optional<SchoolClass> findByName(String name);
}
