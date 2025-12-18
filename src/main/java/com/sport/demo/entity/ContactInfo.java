package com.sport.demo.entity;

import jakarta.persistence.*;

/**
 * Таблиця для зберігання додаткової інформації про зв'язок з користувачем
 */
@Entity
@Table(name = "\"ContactInfo\"")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactID")
    private Integer contactID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "connectionTypeID", nullable = false)
    private ConnectionType connectionType;

    @Column(name = "connectionValue", length = 255, nullable = false)
    private String connectionValue;

    // Getters and Setters
}