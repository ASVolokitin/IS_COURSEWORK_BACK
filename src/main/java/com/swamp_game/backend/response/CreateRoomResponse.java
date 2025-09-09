package com.swamp_game.backend.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CreateRoomResponse extends Response {
    String roomId = null;
}
