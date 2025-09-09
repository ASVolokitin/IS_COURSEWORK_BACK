package com.swamp_game.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.swamp_game.backend.utils.GameStatus;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class GameRoomDTO {
    private String id;
    private String name;
    private String creatorId;
    private int maxPlayers;
    private int currentPlayers;
    private GameStatus status;
    private LocalDateTime createdAt;
    private List<PlayerDTO> players;
    private boolean hasPassword;

    public GameRoomDTO(String id, String name, String creator, int maxPlayers, 
                      int currentPlayers, GameStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.creatorId = creator;
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
        this.status = status;
        this.createdAt = createdAt;
    }
}