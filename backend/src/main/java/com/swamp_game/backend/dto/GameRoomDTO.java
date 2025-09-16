package com.swamp_game.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.swamp_game.backend.model.Player;
import com.swamp_game.backend.utils.GameStatus;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@Builder
public class GameRoomDTO {
    private Long roomId;
    private String RoomName;
    private Long creatorTgId;
    private int maxPlayers;
    private int currentPlayers;
    private GameStatus status;
    private LocalDateTime createdAt;
    private List<Player> players;
    private boolean hasPassword;

    // public GameRoomDTO(Long id, String name, String creator, int maxPlayers, 
    //                   int currentPlayers, GameStatus status, LocalDateTime createdAt) {
    //     this.roomId = id;
    //     this.RoomName = name;
    //     this.creatorTgId = creator;
    //     this.maxPlayers = maxPlayers;
    //     this.currentPlayers = currentPlayers;
    //     this.status = status;
    //     this.createdAt = createdAt;
    // }
}