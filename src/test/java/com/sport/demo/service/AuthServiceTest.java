package com.sport.demo.service;

import com.sport.demo.dto.auth.AuthResponse;
import com.sport.demo.dto.auth.LoginRequest;
import com.sport.demo.dto.auth.RegisterRequest;
import com.sport.demo.entity.Role;
import com.sport.demo.entity.User;
import com.sport.demo.repository.RoleRepository;
import com.sport.demo.repository.UserRepository;
import com.sport.demo.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerFailsWhenUsernameAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existingUser");
        request.setPassword("secret");

        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.register(request));
        assertEquals("User already exists", ex.getMessage());
        verify(roleRepository, never()).findByRoleName(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerAssignsDefaultRoleAndEncodesPassword() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("alice");
        request.setNickname("Ali");
        request.setEmail("alice@example.com");
        request.setPassword("plainPassword");

        Role role = new Role();
        role.setRoleName("USER");

        when(userRepository.existsByUsername("alice")).thenReturn(false);
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(jwtUtils.generateToken(any(User.class))).thenReturn("jwt-token");
        doAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setUserID(7);
            return saved;
        }).when(userRepository).save(any(User.class));

        AuthResponse response = authService.register(request);

        assertEquals("jwt-token", response.getAccessToken());
        assertEquals(7, response.getUserId());
        assertEquals("alice", response.getUsername());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User persisted = userCaptor.getValue();
        assertEquals("encodedPassword", persisted.getPasswordHash());
        assertTrue(persisted.getRoles().contains(role));
    }

    @Test
    void loginThrowsWhenUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setUsername("ghost");
        request.setPassword("irrelevant");

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login(request));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void loginReturnsTokenWhenCredentialsAreValid() {
        LoginRequest request = new LoginRequest();
        request.setUsername("bob");
        request.setPassword("secret");

        User user = new User();
        user.setUserID(99);
        user.setUsername("bob");

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(mock(Authentication.class));
        when(userRepository.findByUsername("bob")).thenReturn(Optional.of(user));
        when(jwtUtils.generateToken(user)).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertEquals("jwt-token", response.getAccessToken());
        assertEquals(99, response.getUserId());
        assertEquals("bob", response.getUsername());

        ArgumentCaptor<UsernamePasswordAuthenticationToken> captor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(authenticationManager).authenticate(captor.capture());
        UsernamePasswordAuthenticationToken token = captor.getValue();
        assertEquals("bob", token.getPrincipal());
        assertEquals("secret", token.getCredentials());
    }
}
