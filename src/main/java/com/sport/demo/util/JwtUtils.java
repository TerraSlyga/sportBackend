package com.sport.demo.util;

import com.sport.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    // Цей ключ має бути дуже довгим (мінімум 256 біт / 32 символи)
    // В реальному проєкті винеси це в application.properties: @Value("${application.security.jwt.secret-key}")
    private final String SECRET_KEY = "mySuperSecretKeyForTheArenaProjectWhichShouldBeVeryLongAndComplex";

    private final long JWT_EXPIRATION = 1000 * 60 * 60 * 24; // 24 години (1 день)

    /**
     * Генерує токен для користувача
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        // Додаємо ID користувача
        claims.put("userId", user.getUserID());

        // Додаємо ролі як список рядків ["USER", "ADMIN"], а не складні об'єкти
        // Припускаємо, що Role має метод getName() або поле name
        // Якщо у Entity Role поле називається 'roleName' або 'name', адаптуй цей рядок:
        claims.put("roles", user.getRoles().stream()
                .map(role -> role.getRoleName()) // або role.getRoleName()
                .collect(Collectors.toList()));

        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Витягує ім'я користувача (username) з токена
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Перевіряє валідність токена
     * 1. Чи співпадає username з токена з username користувача з БД
     * 2. Чи не сплив термін дії токена
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
         try {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    } catch (ExpiredJwtException e) {
        return false;
    }}

    // --- Допоміжні методи ---

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        // Якщо ключ у Base64 - використовуй Decoders.BASE64.decode(SECRET_KEY)
        // Якщо просто текст - використовуй getBytes
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}