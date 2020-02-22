package io.novschola.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.novschola.exception.BadJwtTokenException;
import io.novschola.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Jwt token service class
 * this service is responsible for generating and parsing jwt tokens
 * @author Kacper Szot
 */
@Service
@PropertySource("classpath:application.properties")
@Slf4j
public class JwtTokenService {
    private static final long EXPIRATION_TIME = 60 * 60 * 5 * 1000;

    private Key key;

    public JwtTokenService(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("rol", user.getRoles())
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new BadJwtTokenException(e);
        }
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);

    }

    public String getEmailFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);

    }


    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);

    }

    public Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());

    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    public Key getKey() {
        return key;
    }


}
