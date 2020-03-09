package io.novschola.config;

import io.novschola.security.JwtAuthenticationEntryPoint;
import io.novschola.security.JwtRequestFilter;
import io.novschola.service.JwtUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class
 * @author Kacper Szot
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(16);
    }



    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private JwtUserDetailsService jwtUserDetailsService;
    private JwtRequestFilter jwtRequestFilter;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Lazy
    public SecurityConfig(JwtUserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/v1/auth").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers( "/swagger-ui.html").permitAll()
                .antMatchers("/v1/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/users/*").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/users/*/activate").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/users").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/users").permitAll()
                .antMatchers(HttpMethod.PUT,"/v1/users/*").authenticated()
                .antMatchers(HttpMethod.GET,"/v1/posts/*").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/posts").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/posts").authenticated()
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).userDetailsService(jwtUserDetailsService);
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }
}
