package com.swamp_game.backend.response;

import com.swamp_game.backend.utils.response_status.ResponseStatus;

import lombok.Data;

@Data
public class Response {
    Long requestPlayerId = null;
    ResponseStatus status;
    String message;
}
