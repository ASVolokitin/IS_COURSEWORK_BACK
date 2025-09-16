package com.swamp_game.backend.response;

import java.util.List;

import com.swamp_game.backend.dto.GameRoomDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRoomResponse extends Response{
    Long roomId = null;
    List<GameRoomDTO> rooms = null;
}
