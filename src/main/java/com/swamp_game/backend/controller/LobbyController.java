package com.swamp_game.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamp_game.backend.dto.GameRoomDTO;
import com.swamp_game.backend.service.LobbyService;

@RestController
@RequestMapping("api/lobby")
public class LobbyController {
    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping
    public List<GameRoomDTO> getAllRooms() {
        return lobbyService.getAllRooms();
    }
}
