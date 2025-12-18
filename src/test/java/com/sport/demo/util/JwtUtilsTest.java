package com.sport.demo.util;

import com.sport.demo.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private final JwtUtils jwtUtils = new JwtUtils();

    @Test
    void isTokenValidReturnsTrueForMatchingUser() {
        com.sport.demo.entity.User domainUser = new com.sport.demo.entity.User();
        domainUser.setUserID(1);
        domainUser.setUsername("alice");

        Role role = new Role();
        role.setRoleName("USER");
        domainUser.getRoles().add(role);

        String token = jwtUtils.generateToken(domainUser);
        UserDetails userDetails = User.withUsername("alice")
                .password("irrelevant")
                .authorities(Collections.emptyList())
                .build();

        assertTrue(jwtUtils.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenValidReturnsFalseWhenUsernameDiffers() {
        com.sport.demo.entity.User domainUser = new com.sport.demo.entity.User();
        domainUser.setUserID(2);
        domainUser.setUsername("carol");
        String token = jwtUtils.generateToken(domainUser);

        UserDetails otherUser = User.withUsername("dave")
                .password("irrelevant")
                .authorities(Collections.emptyList())
                .build();

        assertFalse(jwtUtils.isTokenValid(token, otherUser));
    }

    @Test
    void isTokenValidReturnsFalseWhenTokenExpired() throws Exception {
        String secret = readSecretKey(jwtUtils);
        Key signingKey = Keys.hmacShaKeyFor(secret.getBytes());

        String expiredToken = Jwts.builder()
                .setSubject("eve")
                .setIssuedAt(new Date(System.currentTimeMillis() - 2_000))
                .setExpiration(new Date(System.currentTimeMillis() - 1_000))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

        UserDetails userDetails = User.withUsername("eve")
                .password("irrelevant")
                .authorities(Collections.emptyList())
                .build();

        assertFalse(jwtUtils.isTokenValid(expiredToken, userDetails));
    }

    private String readSecretKey(JwtUtils utils) throws Exception {
        Field field = JwtUtils.class.getDeclaredField("SECRET_KEY");
        field.setAccessible(true);
        return (String) field.get(utils);
    }
}
