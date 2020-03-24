package io.novschola.service;

import io.novschola.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Custom UserDetailsService
 * @author Kacper Szot
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s){
        return userService.findByEmail(s);
    }
}
