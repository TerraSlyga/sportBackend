package com.sport.demo.entity;

import jakarta.persistence.*;

/**
 * Таблиця для зберігання інформації про команду
 */
@Entity
@Table(name = "\"Team\"")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamID")
    private Integer teamID;

    @Column(name = "name", length = 20, nullable = false, unique = true)
    private String name;

    @Column(name = "imgPath", length = 255)
    private String imgPath;

    // Getters and Setters
}