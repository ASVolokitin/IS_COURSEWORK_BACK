package com.swamp_game.backend.request;

import lombok.Data;

@Data
public class JoinRoomRequest {
    private String roomId;
    private String playerId;
    private String password; // optional
}