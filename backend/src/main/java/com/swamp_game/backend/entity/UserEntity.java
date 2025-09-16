package com.swamp_game.backend.entity;

import com.swamp_game.backend.utils.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name="users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String tgFirstname; // user first name in Telegram
    private Long tgId;
    private String username; // username in game
    private String password;
    @Enumerated(value=EnumType.STRING)
    private UserRole role;
    private Long winCount;
}
