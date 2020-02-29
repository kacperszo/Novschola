package io.novschola.bootstrap;

import io.novschola.model.Role;
import io.novschola.repositories.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RolesInitializer implements ApplicationListener<ContextRefreshedEvent> {

    RoleRepository roleRepository;

    public RolesInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Role userRole = new Role("ROLE_USER");

        roleRepository.save(userRole);
    }
}
