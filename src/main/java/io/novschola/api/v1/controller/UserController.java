package io.novschola.api.v1.controller;

import io.novschola.api.v1.model.dto.request.CreateUserRequest;
import io.novschola.api.v1.model.dto.request.UpdateUserRequest;
import io.novschola.api.v1.model.dto.response.UserResponse;
import io.novschola.converters.UserToUserResponseConverter;
import io.novschola.exception.BadRequestException;
import io.novschola.exception.ForbiddenException;
import io.novschola.model.User;
import io.novschola.repositories.RoleRepository;
import io.novschola.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * User Rest Controller
 *
 * @author Kacper Szot
 */
@RestController
@RequestMapping("v1/users")
@Slf4j
public class UserController {
    private UserService userService;
    private UserToUserResponseConverter userToUserResponseConverter;
    private RoleRepository roleRepository;

    public UserController(UserService userService, UserToUserResponseConverter userToUserResponseConverter, RoleRepository roleRepository) {
        this.userService = userService;
        this.userToUserResponseConverter = userToUserResponseConverter;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        List<UserResponse> userResponseList = new ArrayList<>();
        userService.getAllActive().forEach(user -> userResponseList.add(userToUserResponseConverter.convert(user)));
        return userResponseList;
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userToUserResponseConverter.convert(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request, BindingResult result) throws MessagingException {

        if (result.hasErrors()) {
            throw new BadRequestException(result);
        }

        return new ResponseEntity<>(userToUserResponseConverter.convert(userService.create(
                new User(
                        request.getEmail(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPassword()))),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PostAuthorize("returnObject.getEmail() == authentication.name")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request, BindingResult result) {

        if (result.hasErrors()) {
            throw new BadRequestException(result);
        }

        User user = userService.findById(id);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBio(request.getBio());

        return userToUserResponseConverter.convert(userService.update(user));
    }

    @GetMapping("/activate/{key}")
    public void activateUser(@PathVariable String key ){
        userService.activate(key);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, Authentication authentication) {

        User user = userService.findById(id);
        if (!(authentication.getName().equals(user.getEmail()) || authentication.getAuthorities().contains(roleRepository.findRoleByRole("ROLE_ADMIN")))) {
            throw new ForbiddenException("Forbidden");
        }

        userService.delete(user);

    }
}
