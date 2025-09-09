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
import com.swamp_game.backend.utils.GameStatus;

@Service
public class LobbyService {
    private final Map<String, GameRoom> rooms = new ConcurrentHashMap<>();
    private final Map<String, String> playerToRoomMap = new ConcurrentHashMap<>();

    public GameRoom createRoom(CreateRoomRequest request) {
        GameRoom room = new GameRoom(request.getRoomName(), request.getCreatorId(), request.getMaxPlayers());
        
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            room.setPassword(request.getPassword());
        }
        
        rooms.put(room.getId(), room);
        
        return room;
    }

    public Optional<GameRoom> joinRoom(String roomId, String playerId, String password) {
        GameRoom room = rooms.get(roomId);
        if (room == null || room.isFull() || room.getStatus() != GameStatus.WAITING) {
            return Optional.empty();
        }

        if (playerToRoomMap.containsKey(playerId)) {
            return Optional.empty();
        // throw new RuntimeException("Player " + playerId + " is already in a room " + 
        //     playerToRoomMap.get(playerId));
    }

        if (room.getPassword() != null && !room.getPassword().equals(password)) {
            return Optional.empty();
        }

        Player player = new Player(playerId);
        if (room.addPlayer(player)) {
            playerToRoomMap.put(playerId, roomId);
            return Optional.of(room);
        }
        
        return Optional.empty();
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
                room.getCurrentPlayers(),
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