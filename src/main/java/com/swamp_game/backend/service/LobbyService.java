package com.swamp_game.backend.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.swamp_game.backend.dto.GameRoomDTO;
import com.swamp_game.backend.dto.PlayerDTO;
import com.swamp_game.backend.model.GameRoom;
import com.swamp_game.backend.model.Player;
import com.swamp_game.backend.request.CreateRoomRequest;
import com.swamp_game.backend.request.JoinRoomRequest;
import com.swamp_game.backend.response.CreateRoomResponse;
import com.swamp_game.backend.response.JoinRoomResponse;
import com.swamp_game.backend.utils.GameStatus;
import com.swamp_game.backend.utils.response_status.JoinRoomResponseStatus;
import com.swamp_game.backend.utils.response_status.ResponseStatus;

@Service
public class LobbyService {

    private final Map<String, GameRoom> rooms = new ConcurrentHashMap<>();
    private final Map<String, String> playerToRoomMap = new ConcurrentHashMap<>();

    public CreateRoomResponse createRoom(CreateRoomRequest request) {

        CreateRoomResponse response = new CreateRoomResponse();
        GameRoom room = new GameRoom(request.getRoomName(), request.getCreatorId(), request.getMaxPlayers());
        response.setRoomId(room.getId());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            room.setPassword(request.getPassword());
        }
        rooms.put(room.getId(), room);
        response.setRequestPlayerId(request.getCreatorId());
        response.setStatus(ResponseStatus.RESPONSE_OK);
        return response;
    }

    public JoinRoomResponse joinRoom(JoinRoomRequest request) {

        JoinRoomResponse response = new JoinRoomResponse();
        response.setStatus(ResponseStatus.RESPONSE_ERROR);
        response.setRequestPlayerId(request.getPlayerId());

        GameRoom room = rooms.get(request.getRoomId());

        if (room == null) {
            response.setMessage(JoinRoomResponseStatus.ROOM_NOT_FOUND.format(request.getRoomId()));
            response.setStatus(ResponseStatus.RESPONSE_ERROR);
            return response;
        }

        if (room.isFull()) {
            response.setMessage(JoinRoomResponseStatus.ROOM_IS_FULL.format(request.getRoomId()));
            response.setStatus(ResponseStatus.RESPONSE_ERROR);
            return response;
        }

        if (room.getStatus() != GameStatus.WAITING) {
            response.setMessage(JoinRoomResponseStatus.ROOM_IS_NOT_WAITING.format(request.getRoomId()));
            response.setStatus(ResponseStatus.RESPONSE_ERROR);
            return response;
        }

        if (room.getPassword() != null && !room.getPassword().equals(request.getPassword())) {
            response.setMessage(JoinRoomResponseStatus.INCORRECT_PASSWORD.format(request.getRoomId()));
            response.setStatus(ResponseStatus.RESPONSE_ERROR);
            return response;
        }

        if (playerToRoomMap.containsKey(request.getPlayerId())) {
            // response.setMessage(JoinRoomResponseStatus.PLAYER_IS_ALREADY_IN_DIFFERENT_ROOM.format(request.getPlayerId(), playerToRoomMap.get(request.getPlayerId())));
            // response.setStatus(ResponseStatus.RESPONSE_ERROR);
            String previousRoomId = playerToRoomMap.get(request.getPlayerId());
            // System.out.println("previousRoomId: " + previousRoomId);
            // System.out.println("playerId: " + request.getPlayerId());
            GameRoom previousRoom = rooms.get(previousRoomId);
            previousRoom.removePlayer(request.getPlayerId());
            rooms.put(previousRoomId, previousRoom);
            // playerToRoomMap.remove(request.getPlayerId());

            // return response;
        }

        Player player = new Player(request.getPlayerId());
        if (room.addPlayer(player)) {
            playerToRoomMap.put(request.getPlayerId(), request.getRoomId());
            response.setMessage(JoinRoomResponseStatus.SUCCESSFUL_JOIN.format(request.getPlayerId(), request.getRoomId()));
            response.setStatus(ResponseStatus.RESPONSE_OK);
            response.setRoomId(request.getRoomId());
            response.setRooms(getAllRooms());

            // Iterator<ConcurrentHashMap.Entry<String, GameRoom>> i = rooms.entrySet().iterator();

            // while (i.hasNext()) {
            //     ConcurrentHashMap.Entry<String, GameRoom> entry = i.next();
            //     System.out.println("Key = " + entry.getKey() + ", value = " + entry.getValue());
            // }

            return response;
        }

        return response;
    }

    public List<GameRoomDTO> getAllRooms() {
        return rooms.values().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<GameRoom> getRoom(String roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }

    private GameRoomDTO convertToDTO(GameRoom room) {
        List<PlayerDTO> playerDTOs = room.getPlayers().stream()
                .map(player -> new PlayerDTO(
                player.getId(),
                player.getUsername(),
                player.getScore(),
                player.isReady(),
                player.getUsername().equals(room.getCreator())
        ))
                .collect(Collectors.toList());

        GameRoomDTO dto = new GameRoomDTO(
                room.getId(),
                room.getName(),
                room.getCreator(),
                room.getMaxPlayers(),
                room.getCurrentPlayersAmount(),
                room.getStatus(),
                room.getCreatedAt()
        );
        dto.setPlayers(playerDTOs);
        dto.setHasPassword(room.getPassword() != null);

        return dto;
    }

    public Optional<String> getPlayerRoomId(String playerName) {
        return Optional.ofNullable(playerToRoomMap.get(playerName));
    }
}
