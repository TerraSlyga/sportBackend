package com.sport.demo.controller;

import com.sport.demo.dto.auth.AuthResponse;
import com.sport.demo.dto.auth.LoginRequest;
import com.sport.demo.dto.auth.RegisterRequest;
import com.sport.demo.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return setCookieAndReturn(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return setCookieAndReturn(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Щоб вийти, ми перезаписуємо куку пустою і з терміном життя 0
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false) // true для HTTPS
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    // Допоміжний метод для встановлення куки
    private ResponseEntity<AuthResponse> setCookieAndReturn(AuthResponse authResponse) {
        // Створюємо HttpOnly Cookie
        ResponseCookie cookie = ResponseCookie.from("accessToken", authResponse.getAccessToken())
                .httpOnly(true) // JavaScript не може прочитати цю куку (захист від XSS)
                .secure(false)  // Став true, якщо в тебе HTTPS (на локалхості можна false)
                .path("/")      // Кука доступна для всього сайту
                .maxAge(24 * 60 * 60) // 1 день
                .sameSite("Strict") // Захист від CSRF
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser(Authentication authentication) {
        // authentication.getName() поверне username з токена
        if (authentication == null || !authentication.isAuthenticated()) {
            // Якщо користувач не залогінений - повертаємо 401 помилку
            // Фронтенд побачить це і зробить setUser(null)
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(authService.getCurrentUser(authentication.getName()));
    }
}