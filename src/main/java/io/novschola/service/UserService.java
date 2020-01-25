package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.model.User;
import io.novschola.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
        return userRepository.save(user);
    }

    public User update(User user) throws Exception{
        if (user.getId() == null) {
            throw new BadRequestException();
        }
        return userRepository.save(user);
    }

}
