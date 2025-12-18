package com.sport.demo.entity;

import jakarta.persistence.*;

/**
 * Таблиця для зберігання контенту, пов'язаного з турніром
 */
@Entity
@Table(name = "\"EventContent\"")
public class EventContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventContentID")
    private Integer eventContentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventID", nullable = false)
    private Event event;

    @Column(name = "contentPath", length = 255, nullable = false)
    private String contentPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentTypeID", nullable = false)
    private ContentType contentType;

    @Column(name = "contentDesc", length = 50)
    private String contentDesc;

    // Getters and Setters
}