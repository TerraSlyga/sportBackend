package com.sport.demo.entity;

import jakarta.persistence.*;

/**
 * Таблиця для зберігання типів контенту
 */
@Entity
@Table(name = "\"ContentType\"")
public class ContentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contentTypeID")
    private Integer contentTypeID;

    @Column(name = "type", length = 50, nullable = false, unique = true)
    private String type;

    // Getters and Setters
}