package io.novschola.api.v1.controller;

import io.novschola.api.v1.model.JwtRequest;
import io.novschola.api.v1.model.JwtResponse;
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

@RestController
@RequestMapping("v1/auth")
@Slf4j
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenService jwtTokenService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping
    public JwtResponse auth(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        }catch (Exception e){
            throw new BadCredentialsException();
        }
        User user = userService.findByEmail(jwtRequest.getEmail());
        return new JwtResponse(jwtTokenService.generateToken(user));
    }


}
