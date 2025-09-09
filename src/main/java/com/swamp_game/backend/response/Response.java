package com.swamp_game.backend.response;

import com.swamp_game.backend.utils.response_status.ResponseStatus;

import lombok.Data;

@Data
public class Response {
    String requestPlayerId = null;
    ResponseStatus status;
    String message;
}
