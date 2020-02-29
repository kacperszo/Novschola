package io.novschola.repositories;

import io.novschola.model.Role;
import org.springframework.data.repository.CrudRepository;
/**
 * Role class repository
 * @author Kacper Szot
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByRole(String role);
}
