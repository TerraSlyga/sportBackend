package com.sport.demo.repository;

import com.sport.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);

    // SELECT count(*) > 0 FROM users WHERE username = ?
    boolean existsByUsername(String username);

    // Якщо треба перевіряти ще й email:
    boolean existsByEmail(String email);
}
