package com.swamp_game.backend.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.swamp_game.backend.utils.GameStatus;

import lombok.Data;

@Data
public class GameRoom {
    private String id;
    private String name;
    private String creator;
    private int maxPlayers;
    private Set<Player> players;
    private Game game;
    private GameStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private String password; // optional

    public GameRoom(String name, String creator, int maxPlayers) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.creator = creator;
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

    public boolean removePlayer(String playerId) {
        return players.removeIf(p -> p.getId().equals(playerId));
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