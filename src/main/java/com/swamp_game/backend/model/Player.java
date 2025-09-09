package com.swamp_game.backend.model;

import java.util.UUID;

import com.swamp_game.backend.utils.PlayerStatus;

import lombok.Data;

@Data
public class Player {
    private String id;
    private String username;
    private int score;
    private boolean ready;
    private PlayerStatus status;

    public Player(String username) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.score = 0;
        this.ready = false;
        this.status = PlayerStatus.CONNECTED;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}