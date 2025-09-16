package com.swamp_game.backend.request;

import com.swamp_game.backend.utils.GameType;

import lombok.Data;

@Data
public class CreateRoomRequest {
    private String roomName;
    private Long creatorId;
    private int maxPlayers;
    private String password; // optional
    private GameType gameType;
}