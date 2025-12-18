package com.sport.demo.entity;

import jakarta.persistence.*;

/**
 * Таблиця для зберігання інформації про матч
 */
@Entity
@Table(name = "\"Match\"")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matchID")
    private Integer matchID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventID", nullable = false)
    private Event event;

    @Column(name = "round", nullable = false)
    private Integer round;

    @Column(name = "participantOneID", nullable = false)
    private Integer participantOneID;

    @Column(name = "participantTwoID", nullable = false)
    private Integer participantTwoID;

    @Column(name = "scoreOne")
    private Integer scoreOne;

    @Column(name = "scoreTwo")
    private Integer scoreTwo;

    @Column(name = "winnerID")
    private Integer winnerID;

    @Column(name = "matchStateID", nullable = false)
    private Integer matchStateID; // Можна зробити зв'язок з окремою таблицею MatchState

    // Getters and Setters
}