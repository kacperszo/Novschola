package io.novschola.repositories;

import io.novschola.model.SchoolClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolClassRepository extends CrudRepository<SchoolClass, Long> {
    Optional<SchoolClass> findByName(String name);
}
