package com.sport.demo.entity;

import jakarta.persistence.*;

/**
 * Таблиця для зберігання стану заходу
 */
@Entity
@Table(name = "\"EventState\"")
public class EventState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventStateID")
    private Integer eventStateID;

    @Column(name = "eventState", length = 50, nullable = false, unique = true)
    private String eventState;

    // Getters and Setters
    public Integer getEventStateID() {
        return eventStateID;
    }

    public void setEventStateID(Integer eventStateID) {
        this.eventStateID = eventStateID;
    }

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }
}