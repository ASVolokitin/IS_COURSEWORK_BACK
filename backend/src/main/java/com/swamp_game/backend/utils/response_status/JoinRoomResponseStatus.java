package com.swamp_game.backend.utils.response_status;

import lombok.Getter;

@Getter
public enum JoinRoomResponseStatus {
    SUCCESSFUL_JOIN("Player '%s' was successfully joined to a room '%s'"),

    ROOM_NOT_FOUND("Room '%s' was not found"),
    ROOM_IS_FULL("Room '%s' is full"),
    ROOM_IS_NOT_WAITING("Room '%s' is currently not waiting for new players"),
    PLAYER_IS_ALREADY_IN_DIFFERENT_ROOM("Player '%s' is already in a room '%s'"),
    INCORRECT_PASSWORD("Room '%s' is private, your password is incorrect");

    private final String statusMessagePattern;

    private JoinRoomResponseStatus(String statusMessage) {
        this.statusMessagePattern = statusMessage;
    }

    public String format(Object... args) {
        return String.format(statusMessagePattern, args);
    }
}
