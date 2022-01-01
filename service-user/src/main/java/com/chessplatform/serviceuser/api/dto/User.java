package com.chessplatform.serviceuser.api.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String password;
    private String role;
    private int eloRating;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getEloRating() {
        return eloRating;
    }

    public void setEloRating(int eloRating) {
        this.eloRating = eloRating;
    }

    public User(com.chessplatform.serviceuser.repo.model.User user)
    {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.eloRating = user.getEloRating();
        this.role = user.getRole().toString();
    }
    public User(String username, String role)
    {
        this.username = username;
        this.role = role;
    }
}
