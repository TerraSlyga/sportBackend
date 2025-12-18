package com.sport.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Таблиця логування змін в правах доступу
 */
@Entity
@Table(name = "\"UserRoleLog\"")
public class UserRoleLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logID")
    private Integer logID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affectedUserID", nullable = false)
    private User affectedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleID", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changedByUserID", nullable = false)
    private User changedByUser;

    @Column(name = "action", length = 20, nullable = false)
    private String action;

    @Column(name = "actionTimestamp", nullable = false)
    private LocalDateTime actionTimestamp;

    // Getters and Setters
}