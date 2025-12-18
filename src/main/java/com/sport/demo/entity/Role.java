package com.sport.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Таблиця для зберігання типів ролей
 */
@Entity
@Table(name = "\"Role\"")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleID")
    private Integer roleID;

    @Column(name = "roleName", length = 50, nullable = false, unique = true)
    private String roleName;

    @Column(name = "roleDesc", length = 255)
    private String roleDesc;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // Getters and Setters

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}