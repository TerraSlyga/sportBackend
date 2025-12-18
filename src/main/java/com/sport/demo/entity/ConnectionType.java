package com.sport.demo.entity;

import jakarta.persistence.*;

/**
 * Таблиця для зберігання типів додаткової інформації про користувачів
 */
@Entity
@Table(name = "\"ConnectionType\"")
public class ConnectionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connectionTypeID")
    private Integer connectionTypeID;

    @Column(name = "connectionTypeName", length = 50, nullable = false, unique = true)
    private String connectionTypeName;

    @Column(name = "connectionPath", length = 50, nullable = false)
    private String connectionPath;

    @Column(name = "connectionDesc", length = 255)
    private String connectionDesc;

    // Getters and Setters
}