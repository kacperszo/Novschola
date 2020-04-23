package io.novschola.api.v1.controller;

import io.novschola.api.v1.model.dto.request.AuthRequest;
import io.novschola.api.v1.model.dto.response.AuthResponse;
import io.novschola.converters.UserToUserResponseConverter;
import io.novschola.exception.BadCredentialsException;
import io.novschola.model.User;
import io.novschola.service.JwtTokenService;
import io.novschola.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Authentication controller
 * Rest controller responsible for user's authentication
 *
 * @author Kacper Szot
 */
@RestController
@RequestMapping("v1/auth")
@Slf4j
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenService jwtTokenService;
    private UserToUserResponseConverter userToUserResponseConverter;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtTokenService jwtTokenService, UserToUserResponseConverter userToUserResponseConverter) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.userToUserResponseConverter = userToUserResponseConverter;
    }

    /**
     * Method is invoked when user sends a post request to auth endpoint, it's responsible for user's authentication and providing them a token and information about logged user
     *
     * @param authRequest AuthRequest
     * @return String when user is correctly authenticated controller returns jwt token
     * @throws BadCredentialsException when provided credentials are not correct controller throws a BadCredentialsException
     */

    @PostMapping
    public AuthResponse auth(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (Exception e) {
            throw new BadCredentialsException();
        }
        User user = userService.findByEmail(authRequest.getEmail());
        return new AuthResponse(jwtTokenService.generateToken(user), userToUserResponseConverter.convert(user));
    }


}
