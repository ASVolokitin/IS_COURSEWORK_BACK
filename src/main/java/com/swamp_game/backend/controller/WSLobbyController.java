package com.swamp_game.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.swamp_game.backend.dto.GameRoomDTO;
import com.swamp_game.backend.model.GameRoom;
import com.swamp_game.backend.request.CreateRoomRequest;
import com.swamp_game.backend.request.JoinRoomRequest;
import com.swamp_game.backend.service.LobbyService;

@RestController
public class WSLobbyController {

    private final LobbyService lobbyService;
    private final SimpMessagingTemplate messagingTemplate;

    public WSLobbyController(LobbyService lobbyService, SimpMessagingTemplate messagingTemplate) {
        this.lobbyService = lobbyService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/room.create")
    @SendTo("/topic/rooms")
    public List<GameRoomDTO> createRoom(@Payload CreateRoomRequest request) {
        try {
            GameRoom room = lobbyService.createRoom(request);
            return lobbyService.getAllRooms();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create a room: " + e.getMessage());
        }
    }

    @MessageMapping("/room.join")
    public void joinRoom(JoinRoomRequest request) {
        Optional<GameRoom> room = lobbyService.joinRoom(request.getRoomId(), request.getPlayerId(), request.getPassword());
        if (!room.isEmpty()) {
            messagingTemplate.convertAndSend("/topic/rooms", lobbyService.getAllRooms());
            messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), 
                lobbyService.getRoom(request.getRoomId()));
        }
    }
}