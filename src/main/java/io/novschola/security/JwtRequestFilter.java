package io.novschola.security;

import io.novschola.exception.BadJwtTokenException;
import io.novschola.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Custom GenericFilterBean used to authenticate user by jwt token
 * @author Kacper Szot
 */
@Component
@Slf4j
public class JwtRequestFilter extends GenericFilterBean {

    private JwtTokenService jwtTokenService;
    private UserDetailsService userDetailsService;

    public JwtRequestFilter(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest httpServletRequest) {
        String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isNotEmpty(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
            try {
                String userEmail = jwtTokenService.getEmailFromToken(requestTokenHeader.replace("Bearer ", ""));
                if (StringUtils.isNotEmpty(userEmail)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                    return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                }
            } catch (BadJwtTokenException | UsernameNotFoundException e) {
                log.debug("unauthorized jwt token");
            }
        }
        return null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpServletRequest);
        UsernamePasswordAuthenticationToken authentication = getAuthentication(wrappedRequest);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(wrappedRequest, servletResponse);
        this.resetAuthenticationAfterRequest();
    }

    private void resetAuthenticationAfterRequest() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
