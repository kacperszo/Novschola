package io.novschola.repositories;

import io.novschola.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * User repository
 * @author Kacper Szot
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Iterable<User> findAllByActiveTrue();

    Optional<User> findByEmail(String email);

    Optional<User> findByFirstName(String firstName);

    Optional<User> findByLastName(String lastName);

    Optional<User> findByActivationKey(String activationKey);

    boolean existsByEmail(String email);
}
