package com.spotmini.users.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isSpecial;

    public UserEntity() {
    }

    public UserEntity(String username, String password, boolean isSpecial) {
        this.username = username;
        this.password = password;
        this.isSpecial = isSpecial;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setAdmin(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }
}

