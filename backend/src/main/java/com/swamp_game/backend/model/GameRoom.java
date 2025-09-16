package com.swamp_game.backend.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.swamp_game.backend.utils.GameStatus;

import lombok.Data;

@Data
public class GameRoom {
    private Long roomId;
    private String roomName;
    private Long creatorTgId;
    private int maxPlayers;
    private Set<Player> players;
    private Game game;
    private GameStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private String password; // optional

    public GameRoom(String name, Long creatorTgId, int maxPlayers) {
        this.roomId = 1L;
        this.roomName = name;
        this.creatorTgId = creatorTgId;
        this.maxPlayers = maxPlayers;
        this.players = new HashSet<>();
        this.status = GameStatus.WAITING;
        this.createdAt = LocalDateTime.now();
        this.game = new Game();
    }

    public boolean addPlayer(Player player) {
        if (players.size() >= maxPlayers || status != GameStatus.WAITING) {
            return false;
        }
        return players.add(player);
    }

    public boolean removePlayer(Long playerId) {
        return players.removeIf(p -> p.getTgId().equals(playerId));
    }

    public int getCurrentPlayersAmount() {
        return players.size();
    }

    public boolean isFull() {
        return players.size() >= maxPlayers;
    }

    public boolean canStartGame() {
        return status == GameStatus.WAITING && players.size() >= 2; // Минимум 2 игрока
    }
}