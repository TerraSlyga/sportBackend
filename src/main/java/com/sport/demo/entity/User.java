package com.sport.demo.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Таблиця для зберігання базової інформації про користувачів
 */
@Entity
@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Integer userID;

    @Column(name = "username", length = 20, nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "passwordHash", length = 255, nullable = false) // Змінено довжину для хешу
    private String passwordHash;

    @Column(name = "nickname", length = 20, nullable = false, unique = true)
    private String nickname;

    @OneToMany(mappedBy = "user")
    private Set<ContactInfo> contactInfos;

    @OneToMany(mappedBy = "organizer")
    private Set<Event> organizedEvents;

    @ManyToMany
    @JoinTable(
            name = "\"RoleToUser\"",
            joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "roleID")
    )
    private Set<Role> roles = new HashSet<>();

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public Set<ContactInfo> getContactInfos() {
        return contactInfos;
    }

    public Set<Event> getOrganizedEvents() {
        return organizedEvents;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContactInfos(Set<ContactInfo> contactInfos) {
        this.contactInfos = contactInfos;
    }

    public void setOrganizedEvents(Set<Event> organizedEvents) {
        this.organizedEvents = organizedEvents;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}