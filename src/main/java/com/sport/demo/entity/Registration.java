package com.sport.demo.entity;

import jakarta.persistence.*;

/**
 * Таблиця для зберігання реєстрацій на події
 */
@Entity
@Table(name = "\"Registration\"")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registrationID")
    private Integer registrationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventID", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamID")
    private Team team;

    // Getters and Setters
}