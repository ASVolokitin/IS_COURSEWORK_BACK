package com.swamp_game.backend.model;

import lombok.Data;

@Data
public class Player {
    private Long tgId;
    private String username;
    private Long winCount;

    public Player(Long tgId, String username, Long winCount, boolean isCreator) {
        this.tgId = tgId;
        this.username = username;
        this.winCount = winCount;
    }

    public Player(Long tgId) {
        this.tgId = tgId;
        this.username = "guest";
        this.winCount = 0L;
    }
}