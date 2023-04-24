package com.spotmini.users.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "username")
    private String username;

    @Column (name = "password")
    private String password;

    @Column (name = "isSpecial")
    private boolean isSpecial;

    public UserModel() {
    }

    public UserModel(String username, String password, boolean isSpecial) {
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

