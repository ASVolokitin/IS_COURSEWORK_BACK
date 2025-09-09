package com.swamp_game.backend.dto;

import lombok.Data;

@Data
public class PlayerDTO {
    private String id;
    private String username;
    private int score;
    private boolean ready;
    private boolean isCreator;

    public PlayerDTO(String id, String username, int score, boolean ready, boolean isCreator) {
        this.id = id;
        this.username = username;
        this.score = score;
        this.ready = ready;
        this.isCreator = isCreator;
    }
}