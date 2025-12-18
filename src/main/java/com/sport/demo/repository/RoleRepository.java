package com.sport.demo.repository;

import com.sport.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // SELECT * FROM roles WHERE name = ?
    // Важливо: переконайся, що в твоєму Entity класі Role поле називається "name"
    // Якщо в Entity поле називається "roleName", то метод має бути findByRoleName(String roleName)
    Optional<Role> findByRoleName(String roleName);
}