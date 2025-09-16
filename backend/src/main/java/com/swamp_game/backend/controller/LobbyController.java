package com.swamp_game.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamp_game.backend.dto.GameRoomDTO;
import com.swamp_game.backend.request.CreateRoomRequest;
import com.swamp_game.backend.request.JoinRoomRequest;
import com.swamp_game.backend.response.CreateRoomResponse;
import com.swamp_game.backend.response.JoinRoomResponse;
import com.swamp_game.backend.service.LobbyService;
import com.swamp_game.backend.utils.response_status.ResponseStatus;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/lobby")
public class LobbyController {

    private final LobbyService lobbyService;
    private final SimpMessagingTemplate messagingTemplate;

    public LobbyController (LobbyService lobbyService, SimpMessagingTemplate messagingTemplate) {
        this.lobbyService = lobbyService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public List<GameRoomDTO> getAllRooms() {
        return lobbyService.getAllRooms();
    }

    @PostMapping("/join")
    public ResponseEntity<JoinRoomResponse> joinRoom(@RequestBody JoinRoomRequest request, HttpSession session) {

        JoinRoomResponse response = lobbyService.joinRoom(request);
        messagingTemplate.convertAndSend("/topic/rooms", lobbyService.getAllRooms());

        if (response.getStatus() == ResponseStatus.RESPONSE_OK) return ResponseEntity.ok(response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }   

    @PostMapping("/create")
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody CreateRoomRequest request, HttpSession session) {
        CreateRoomResponse response = lobbyService.createRoom(request);
        messagingTemplate.convertAndSend("/topic/rooms", lobbyService.getAllRooms());
        if (response.getStatus() == ResponseStatus.RESPONSE_OK) return ResponseEntity.ok(response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}