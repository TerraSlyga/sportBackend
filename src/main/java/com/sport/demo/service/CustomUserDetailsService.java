package com.sport.demo.service;

import com.sport.demo.entity.User;
import com.sport.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional // Важливо, щоб завантажити ледачі (Lazy) колекції ролей
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Шукаємо користувача в нашій базі
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 2. Конвертуємо дані користувача в об'єкт, який розуміє Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(), // Важливо: передаємо захешований пароль з БД
                mapRolesToAuthorities(user.getRoles()) // Перетворюємо ролі
        );
    }

    // Допоміжний метод для конвертації ролей
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<com.sport.demo.entity.Role> roles) {
        return roles.stream()
                // Тут ми беремо назву ролі. Перевір, як називається поле в твоєму entity Role (name чи roleName?)
                // Якщо поле називається roleName, пиши role.getRoleName()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }
}
