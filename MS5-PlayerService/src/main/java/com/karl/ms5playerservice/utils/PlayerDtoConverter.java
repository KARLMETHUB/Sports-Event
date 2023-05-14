package com.karl.ms5playerservice.utils;

import com.karl.ms5playerservice.dto.PlayerDTO;
import com.karl.ms5playerservice.entity.Player;

public class PlayerDtoConverter {

    private PlayerDtoConverter() {}

    public static Player toEntity(PlayerDTO p) {
        return Player
                .builder()
                .playerId(p.getPlayerId())
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .country(p.getCountry())
                .teamId(p.getTeamId())
                .build();
    }

    public static PlayerDTO toDto(Player p) {
        return PlayerDTO
                .builder()
                .playerId(p.getPlayerId())
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .country(p.getCountry())
                .teamId(p.getTeamId())
                .build();
    }
}
