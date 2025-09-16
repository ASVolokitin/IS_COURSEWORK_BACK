package com.swamp_game.backend.request;

import lombok.Data;

@Data
public class JoinRoomRequest {
    private Long roomId;
    private Long playerId;
    private String password; // optional
}