package com.swamp_game.backend.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.swamp_game.backend.service.GameService;


// TODO: контроллер для самой игры
@Controller
public class WSGameController {

    private final GameService gameService;

    public WSGameController(GameService gameService, SimpMessagingTemplate messagingTemplate) {
        this.gameService = gameService;
    }
}