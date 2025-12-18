package com.sport.demo.service;

import com.sport.demo.dto.auth.AuthResponse;
import com.sport.demo.dto.auth.LoginRequest;
import com.sport.demo.dto.auth.RegisterRequest;
import com.sport.demo.entity.User;
import com.sport.demo.entity.Role;
import com.sport.demo.repository.UserRepository;
import com.sport.demo.repository.RoleRepository;
import com.sport.demo.util.JwtUtils; // Самописний клас для генерації токенів
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // Для таблиці Role
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // --- РЕЄСТРАЦІЯ ---
    public AuthResponse register(RegisterRequest request) {
        // 1. Перевірка чи існує юзер
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        // 2. Створення сутності User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        // ХЕШУЄМО ПАРОЛЬ!
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // 3. Призначення ролі (за замовчуванням USER)
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));
        user.getRoles().add(userRole); // Припускаємо, що в тебе @ManyToMany налаштовано в Entity

        // 4. Збереження
        userRepository.save(user);

        // 5. Одразу логінимо і повертаємо токен (або просто повертаємо success)
        String token = jwtUtils.generateToken(user);
        return new AuthResponse(token, user.getUserID(), user.getUsername());
    }

    // --- ЛОГІН ---
    public AuthResponse login(LoginRequest request) {
        // 1. Spring Security сам перевіряє логін/пароль
        // Якщо пароль не підійде - викинеться помилка (BadCredentialsException)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Якщо ми тут - значить пароль вірний. Шукаємо юзера для токена
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Генеруємо токен
        String token = jwtUtils.generateToken(user);

        return new AuthResponse(token, user.getUserID(), user.getUsername());
    }

    public AuthResponse getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Ми повертаємо дані користувача.
        // Токен тут null, тому що клієнт вже має дійсний токен у Cookie,
        // нам потрібно лише підтвердити, хто це, і віддати ID/Username.
        return new AuthResponse(null, user.getUserID(), user.getUsername());
    }
}
