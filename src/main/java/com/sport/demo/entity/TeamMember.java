package com.sport.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Таблиця для зберігання зв'язків між користувачами та командами (роль в команді)
 */
@Entity
@Table(name = "\"TeamMember\"")
public class TeamMember {

    @EmbeddedId
    private TeamMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userID")
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamID")
    @JoinColumn(name = "teamID")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleID")
    private Role role;

    // Getters and Setters
}

@Embeddable
class TeamMemberId implements Serializable {
    @Column(name = "userID")
    private Integer userID;
    @Column(name = "teamID")
    private Integer teamID;

    // hashCode, equals, getters, setters
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamMemberId that = (TeamMemberId) o;
        return Objects.equals(userID, that.userID) &&
                Objects.equals(teamID, that.teamID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, teamID);
    }
}