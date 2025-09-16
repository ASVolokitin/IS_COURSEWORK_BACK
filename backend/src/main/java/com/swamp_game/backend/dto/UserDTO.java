package com.swamp_game.backend.dto;

import com.swamp_game.backend.utils.UserRole;

public record UserDTO (Long tgId, String tgFirstname, String username, String password, UserRole role) {} 
