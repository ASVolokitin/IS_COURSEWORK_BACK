package com.swamp_game.backend.model;

import java.util.HashMap;
import java.util.Map;

import com.swamp_game.backend.utils.GameState;
import com.swamp_game.backend.utils.GameType;

import lombok.Data;

@Data
public class Game {
    private GameType type;
    private Map<String, Integer> scores;
    private GameState state;
    private int currentRound;
    private int totalRounds;
    private String currentTurnPlayerId;

    public Game() {
        this.type = GameType.DEFAULT;
        this.scores = new HashMap<>();
        this.state = GameState.NOT_STARTED;
        this.currentRound = 0;
        this.totalRounds = 5;
    }

    public void startGame() {
        this.state = GameState.IN_PROGRESS;
        this.currentRound = 1;
    }

    public void endGame() {
        this.state = GameState.FINISHED;
    }

    public void nextRound() {
        if (currentRound < totalRounds) {
            currentRound++;
        } else {
            endGame();
        }
    }

    public void updateScore(String playerId, int points) {
        scores.put(playerId, scores.getOrDefault(playerId, 0) + points);
    }
}