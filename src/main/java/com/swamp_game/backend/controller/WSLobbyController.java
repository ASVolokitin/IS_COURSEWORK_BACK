// package com.swamp_game.backend.controller;

// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.web.bind.annotation.RestController;

// import com.swamp_game.backend.service.LobbyService;

// @RestController
// public class WSLobbyController {

//     private final LobbyService lobbyService;
//     private final SimpMessagingTemplate messagingTemplate;

//     public WSLobbyController(LobbyService lobbyService, SimpMessagingTemplate messagingTemplate) {
//         this.lobbyService = lobbyService;
//         this.messagingTemplate = messagingTemplate;
//     }

//     @MessageMapping("/room.create")
//     @SendTo("/topic/rooms")
//     public List<GameRoomDTO> createRoom(@Payload CreateRoomRequest request) {
//         CreateRoomResponse response = lobbyService.createRoom(request);
//         return response

//     }

//     @MessageMapping("/room.join")
//     public void joinRoom(JoinRoomRequest request, @Header("simpSessionId") String sessionId) {
//         JoinRoomResponse joinRoomResponse = lobbyService.joinRoom(request.getRoomId(), request.getPlayerId(), request.getPassword());
//         messagingTemplate.convertAndSendToUser(sessionId, "/queue", sessionId);
//         if (joinRoomResponse.getStatus() == ResponseStatus.RESPONSE_OK) {
//             messagingTemplate.convertAndSend("/topic/rooms", lobbyService.getAllRooms());
//             // messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), lobbyService.getRoom(request.getRoomId()));
//         }        
        
//     }
// }