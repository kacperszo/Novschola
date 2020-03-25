package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
import io.novschola.model.Role;
import io.novschola.model.User;
import io.novschola.repositories.RoleRepository;
import io.novschola.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/**
 * User service
 * @author Kacper Szot
 */
@Service
@Validated
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JavaMailSender javaMailSender;
    private String novscholaUrl;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender, @Value("${novschola.url}") String novscholaUrl) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
        this.novscholaUrl = novscholaUrl;
    }

    public User create(User user) throws MessagingException {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new BadRequestException("user already exist");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActivationKey(RandomStringUtils.randomAlphanumeric(20));
        user.setActive(false);
        user.setRoles(Arrays.asList(roleRepository.findRoleByRole("ROLE_USER")));


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setTo(user.getEmail());
        helper.setSubject("Novschola account activation");
        helper.setText("To activate account click <a href=\" "+novscholaUrl+"/v1/users/activate/"+user.getActivationKey()+"\"> here </a>", true);

        javaMailSender.send(mimeMessage);


        return userRepository.saveAndFlush(user);
    }

    public User update(User user) {
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            throw new BadRequestException();
        }
        return userRepository.saveAndFlush(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public User findActiveById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() && user.get().isActive()){
            return user.get();
        }else{
            throw new ItemNotFoundException();
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(ItemNotFoundException::new);
    }

    public List<User> getAllActive() {
        return StreamSupport
                .stream(userRepository.findAllByActiveTrue()
                                .spliterator(),
                        false)
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

    public List<User> findAll() {
        return new ArrayList<>(userRepository.
                findAll());
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }


    public void delete(User user) {
        userRepository.delete(user);
    }
}
