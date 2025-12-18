package com.sport.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Таблиця логування змін в деталях заходу
 */
@Entity
@Table(name = "\"EventLog\"")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logID")
    private Integer logID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventID", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changedByUserID", nullable = false)
    private User changedByUser;

    @Column(name = "fieldName", length = 50, nullable = false)
    private String fieldName;

    @Column(name = "oldValue", columnDefinition = "TEXT")
    private String oldValue;

    @Column(name = "newValue", columnDefinition = "TEXT")
    private String newValue;

    @Column(name = "changeTimestamp", nullable = false)
    private LocalDateTime changeTimestamp;

    // Getters and Setters
}