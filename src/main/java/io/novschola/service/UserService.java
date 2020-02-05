package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.User;
import io.novschola.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Validated
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User create(User user) {
        user.setId(null);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActivationKey(RandomStringUtils.randomAlphanumeric(20));
        user.setActive(false);
        user.setCreateDate(null);
        return userRepository.save(user);
    }

    public User update(User user) {
        if (user.getId() == null) {
            throw new BadRequestException();
        }
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(ItemNotFoundException::new);
    }

    public List<User> getAllActive() {
        Iterable<User> users = userRepository.findAllByActiveTrue();
        return StreamSupport
                .stream(users.spliterator(), false)
                .collect(Collectors.toList());
    }

    public User findByFirstName(String name) {
        return userRepository.findByFirstName(name).orElseThrow(ItemNotFoundException::new);
    }

    public User findByLastName(String name) {
        return userRepository.findByLastName(name).orElseThrow(ItemNotFoundException::new);
    }

    public User findByActivationKey(String key) {
        return userRepository.findByActivationKey(key).orElseThrow(ItemNotFoundException::new);
    }

    public User activate(String key) {
        User user = findByActivationKey(key);
        user.setActive(true);
        return update(user);
    }


}
